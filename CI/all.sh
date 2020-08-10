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
	
	echo $pr_num
	
	mkdir $pr_num
	cd $pr_num
	
	mkdir eat
	
	: '
	cd eat 
	
	git clone "https://github.com/EAT-JBCOMMUNITY/EAT/"
	cd *
	
	git fetch origin +refs/pull/$pr_num/merge;
	git checkout FETCH_HEAD;	
	
	cd ../../
	'
	
	#Get PR's description
	prs=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls/$pr_num)
	prs=$(echo $prs | grep -Po '"body":.*?[^\\]",');
	prs=$(echo $prs | grep -Po '\[.*\]');

	echo "spr--> "$prs
	 
	while IFS=";" read -ra description_lines
	do
		spr_found=false
		for i in "${description_lines[@]}"; do
			
			if [[ $i == *"SPR"* ]]; then
				spr_found=true
			
				i=$(echo $i | grep -Po '\[.*\]');
		  		#i=${i#*SPR}
		  		#echo "array data: "$i

		  		org=$(echo $i | grep -Po 'org:[^,]*');
		  		org=$(echo $org | grep -Po '[^:]*$');
		  		#echo "org: "$org
		  		
		  		repo=$(echo $i | grep -Po 'repo:[^,]*');
		  		repo=$(echo $repo | grep -Po '[^:]*$');
		  		#echo "repo: "$repo
		  		
		  		branch=$(echo $i | grep -Po 'branch:[^,]*');
		  		branch=$(echo $branch | grep -Po '[^:]*$');
		  		#echo "branch: "$branch
		  		
		  		pr=$(echo $i | grep -Po 'PR:[^\]]*');
		  		pr=$(echo $pr | grep -Po '[^:]*$');
		  		#echo "pr: "$pr
		  		echo $org $repo $branch $pr
		  		
		  		mkdir "server-"$pr
		  		cd "server-"$pr

				: '
		  		git clone "https://github.com/"$org"/"$repo
		  		cd *
		  		git checkout $branch
		  		git fetch origin +refs/pull/$pr/merge;
				git checkout FETCH_HEAD;
				

				mvn clean install -D$TEST_CATEGORY -Dstandalone
				'
				cd ../
			fi	
		done
	done <<< $prs
	
	cd ../
	
	: '
	#No SPR
	if [ $spr_found == false ]; then
		mvn clean install -D$TEST_CATEGORY -Dstandalone
	fi
	'
	
	echo $i >> checked_PRs.txt
done
