#!/bin/bash

set -e

#Get all Testsuite PRs
eat_prs_get=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls?state=open);
eat_prs_number=$(echo $eat_prs_get | grep -Po '"number":.*?[^\\],');
eat_arr+=($(echo $eat_prs_number | grep -Po '[0-9]*')) ;

checked_prs_file="checked_PRs.txt"

if ! [ -r $checked_prs_file ]; then
	>> $checked_prs_file
fi

#Read file lines to array
mapfile -t checked_arr < checked_PRs.txt

echo "All PRs to be checked:"
echo ""

for pr_num in "${eat_arr[@]}"
do
	echo $pr_num" "
done

echo ""

for pr_num in "${eat_arr[@]}"
do
	checked=false
	for j in "${checked_arr[@]}"
	do
		if [ $pr_num == $j ]; then
			checked=true
			break
		fi
	done
	
	if [ $checked == true ]; then
		continue
	fi
	
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

