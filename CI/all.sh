#!/bin/bash

#set -e

if [ -z "$AT" ]; then
	echo "Define AT (github)"
	echo ""
	echo "Example"
	echo "export AT=..."
	exit 1;
fi

if [ -z "$PROGRAM" ]; then
	echo "Define program (github)"
	echo ""
	echo "Example"
	echo "export PROGRAM=..."
	exit 1;
fi

#Split git url
url_arr+=($(echo $AT | grep -Po '[^\/]+'))
org_at=${url_arr[2]}
repo_at=$(echo ${url_arr[3]} | grep -Po '^[^.]+')

#Create files
to_check_prs_file="to_check_PRs.txt"
checked_prs_file="checked_PRs.txt"

if ! [ -r $to_check_prs_file ]; then
	>> $to_check_prs_file
fi

if ! [ -r $checked_prs_file ]; then
	>> $checked_prs_file
fi

if [ "$1" == "reset" ]; then
	> $to_check_prs_file

	#Get all Testsuite PRs
	at_prs_get=$(curl -s -n https://api.github.com/repos/$org_at/$repo_at/pulls?state=open);
	at_prs_number=$(echo $at_prs_get | grep -Po '"number":.*?[^\\],');
	at_arr+=($(echo $at_prs_number | grep -Po '[0-9]*')) ;
	
	for pr_num in "${at_arr[@]}"
	do
		echo $pr_num >> $to_check_prs_file
	done
fi

if [ "$1" == "comment" ]; then
	if [ -z "$GITHUB_TOKEN" ]; then
	    echo "Authentication failed: Github Access Token Not Found"
	    exit 1;
	fi
	
	function comment {
		body=""
		if [ "$1" == true ]; then
	 		body+="Build Success" 
		else
			body+="Build Failed" 
		fi
		
		body+=", "$(date '+%Y-%m-%d %H:%M:%S')
		
		if ! [ -z "$2" ]; then
	 		body+=", $2"
		fi

		comment=$(
			curl -s --request POST "https://api.github.com/repos/$org_at/$repo_at/issues/$pr_num/comments" \
			--header 'Content-Type: application/json' \
			--header 'Authorization: token '$GITHUB_TOKEN \
			--data '{"body": "'"$body"'"}'
			)

	}
fi

#Read file lines to array
mapfile -t to_check_arr < $to_check_prs_file
mapfile -t checked_arr < $checked_prs_file

echo "PRs to be checked:"
echo ""

for pr_num in "${to_check_arr[@]}"
do
	echo $pr_num" "
done

echo ""

for pr_num in "${to_check_arr[@]}"
do	
	mkdir $pr_num
	cd $pr_num
	
	mkdir "at"
	cd at 
	
	git clone "https://github.com/$org_at/$repo_at/"
	cd *
	
	git checkout .;
	git fetch origin +refs/pull/$pr_num/merge;
	git checkout FETCH_HEAD;	
	git pull --rebase origin master;
	
	cd ../../
	
	#Get PR's description
	prs=$(curl -s -n https://api.github.com/repos/$org_at/$repo_at/pulls/$pr_num)
	prs=$(echo $prs | grep -Po '"body":.*?[^\\]",');
	
	spr_found=false
	if prs=$(echo $prs | grep -Po '\[.*\]'); then
	
		#Split lines into an array
		IFS=";" read -ra description_lines <<< $prs
		
		for i in "${description_lines[@]}";
		do
			
			if [[ $i == *"SPR"* ]]; then
				spr_found=true
			
				i=$(echo $i | grep -Po '\[.*\]');
				
				spr_counter=$(echo "${i:5:${#i}}")

		  		org=$(echo $i | grep -Po 'org:[^,]*');
		  		org=$(echo $org | grep -Po '[^:]*$');
		  		
		  		repo=$(echo $i | grep -Po 'repo:[^,]*');
		  		repo=$(echo $repo | grep -Po '[^:]*$');
		  		
		  		branch=$(echo $i | grep -Po 'branch:[^,]*');
		  		branch=$(echo $branch | grep -Po '[^:]*$');
		  		
		  		pr=$(echo $i | grep -Po 'PR:[^\]]*');
		  		pr=$(echo $pr | grep -Po '[^:]*$');
		  		
		  		echo "SPR Data: "$org $repo $branch $pr
		  		
		  		mkdir "program-"$pr
		  		cd "program-"$pr

		  		git clone "https://github.com/"$org"/"$repo
		  		cd *
		  		git checkout $branch
		  		git fetch origin +refs/pull/$pr/merge;
				git checkout FETCH_HEAD;
				git pull --rebase origin $branch;
				
				mvn clean install -DskipTests
				
				server_pom=$(<pom.xml)
				version=$(echo $server_pom | grep -Po '<version>[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*<\/version>');
				version=$(echo $version | grep -Po '[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*');
				export JBOSS_VERSION=$version
				export JBOSS_FOLDER=$PWD/dist/target/wildfly-$JBOSS_VERSION/
				
				cd ../../
				
				cd at/*
				mvn clean install -Dwildfly -Dstandalone
				
				#Maven return code
				if [ "$?" -eq 0 ] ; then
					#OK
					if [ "$1" == "comment" ]; then
						comment true "SPR:"$spr_counter
					fi
				else
					#NOT OK
					if [ "$1" == "comment" ]; then
						comment false "SPR:"$spr_counter
					fi
				fi

				cd ../../
			fi	
		done
	fi

	#No SPR
	if [ $spr_found == false ]; then
		echo "SPR not found, loading default server"
		
		mkdir "program"
  		cd program

  		git clone $PROGRAM
  		cd *
  		git checkout master
		
		mvn clean install -DskipTests
		
		server_pom=$(<pom.xml)
		version=$(echo $server_pom | grep -Po '<version>[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*<\/version>');
		version=$(echo $version | grep -Po '[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*');
		export JBOSS_VERSION=$version
		export JBOSS_FOLDER=$PWD/dist/target/wildfly-$JBOSS_VERSION/
		
		cd ../../
		
		cd at/*
		mvn clean install -Dwildfly -Dstandalone
		
		#Maven return code
		if [ "$?" -eq 0 ] ; then
			#OK
			if [ "$1" == "comment" ]; then
				comment true
			fi
		else
			#NOT OK
			if [ "$1" == "comment" ]; then
				comment false
			fi
		fi
		
		cd ../../
	fi
	
	cd ../
	
	echo $pr_num >> checked_PRs.txt	
done

