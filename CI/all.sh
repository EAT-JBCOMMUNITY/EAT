#!/bin/bash

set -e

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
	eat_prs_get=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls?state=open);
	eat_prs_number=$(echo $eat_prs_get | grep -Po '"number":.*?[^\\],');
	eat_arr+=($(echo $eat_prs_number | grep -Po '[0-9]*')) ;
	
	for pr_num in "${eat_arr[@]}"
	do
		echo $pr_num >> $to_check_prs_file
	done
fi

if [ "$1" == "comment" ]; then
	if [ -z "$GITHUB_TOKEN" ]; then
	    echo "Authentication failed: Github Access Token Not Found"
	    exit 1;
	fi
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
	
	mkdir "eat"
	cd eat 
	
	git clone "https://github.com/EAT-JBCOMMUNITY/EAT/"
	cd *
	
	git checkout .;
	git fetch origin +refs/pull/$pr_num/merge;
	git checkout FETCH_HEAD;	
	git pull --rebase origin master;
	
	cd ../../
	
	#Get PR's description
	prs=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls/$pr_num)
	prs=$(echo $prs | grep -Po '"body":.*?[^\\]",');
	
	if prs=$(echo $prs | grep -Po '\[.*\]'); then
	
		while IFS=";" read -ra description_lines
		do
			spr_found=false
			for i in "${description_lines[@]}"; do
				
				if [[ $i == *"SPR"* ]]; then
					spr_found=true
				
					i=$(echo $i | grep -Po '\[.*\]');

			  		org=$(echo $i | grep -Po 'org:[^,]*');
			  		org=$(echo $org | grep -Po '[^:]*$');
			  		
			  		repo=$(echo $i | grep -Po 'repo:[^,]*');
			  		repo=$(echo $repo | grep -Po '[^:]*$');
			  		
			  		branch=$(echo $i | grep -Po 'branch:[^,]*');
			  		branch=$(echo $branch | grep -Po '[^:]*$');
			  		
			  		pr=$(echo $i | grep -Po 'PR:[^\]]*');
			  		pr=$(echo $pr | grep -Po '[^:]*$');
			  		
			  		echo "SPR Data: "$org $repo $branch $pr
			  		
			  		mkdir "server-"$pr
			  		cd "server-"$pr

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
					
					cd eat/*
					mvn clean install -Dwildfly -Dstandalone
					
					#Maven return code
					if [ "$?" -eq 0 ] ; then
						#OK
						if [ "$1" == "comment" ]; then
							comment=$(
							curl -s --request POST 'https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/issues/'$pr_num'/comments' \
							--header 'Content-Type: application/json' \
							--header 'Authorization: token '$GITHUB_TOKEN \
							--data '{"body": "Build Success"}'
							)
						fi
					else
						#NOT OK
						if [ "$1" == "comment" ]; then
							comment=$(
							curl -s --request POST 'https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/issues/'$pr_num'/comments' \
							--header 'Content-Type: application/json' \
							--header 'Authorization: token '$GITHUB_TOKEN \
							--data '{"body": "Build Failed"}'
							)
						fi
					fi

					cd ../../
				fi	
			done
		done <<< $prs
	fi
		
	cd ../
	: '
	#No SPR
	if [ $spr_found == false ]; then
		mvn clean install -D$TEST_CATEGORY -Dstandalone
	fi
	'
	echo $pr_num >> checked_PRs.txt	
done

