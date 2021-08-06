#!/bin/bash

#set -e

if [ -z "$TEST_CATEGORY" ]; then
    TEST_CATEGORY=wildfly
fi

if [ -z "$PROGRAM_BUILD" ]; then
    PROGRAM_BUILD=true
fi

if [ -z "$AT_BRANCH" ]; then
    AT_BRANCH=master
fi

if [ -z "$PROGRAM_BRANCH" ]; then
    PROGRAM_BRANCH=master
fi

at_file="at_path.txt"
program_file="program_path.txt"

if ! [ -r $program_file ]; then
	>> $program_file
fi
program_path=$(sed '1q;d' $program_file)

if ! [ -r $at_file ]; then
	>> $at_file
fi
at_path=$(sed '1q;d' $at_file)

at_file=$(realpath $at_file)
program_file=$(realpath $program_file)

if [ -z "$PROGRAM" ]; then
	echo "Define program (github)"
	echo ""
	echo "Example"
	echo "export PROGRAM=..."
	exit 1;
fi

if [ -z "$AT" ]; then
	echo "Define AT (github)"
	echo ""
	echo "Example"
	echo "export AT=..."
	exit 1;
fi

program_pr_set=false
if ! [ -z "$PROGRAM_PR" ] && [ "$PROGRAM_PR" -gt 0 ]; then
	program_pr_set=true
fi

#Check AT PR status
url_at_arr+=($(echo $AT | grep -Po '[^\/]+'))
org_at=${url_at_arr[2]}
repo_at=$(echo ${url_at_arr[3]} | grep -Po '^[^.]+')
at_prs_get=$(curl -s -n "https://api.github.com/repos/$org_at/$repo_at/pulls?state=open");
at_prs_number=$(echo $at_prs_get | grep -Po '"number":.*?[^\\],');
at_arr+=( $(echo $at_prs_number | grep -Po '[0-9]*')) ;

at_pr_found=false

for i in "${at_arr[@]}"
do
	if [[ $i == $AT_PR ]]; then
		at_pr_found=true;
		break
	fi
done

if [ $at_pr_found == false ]; then
	echo "Pull Request not found."
fi

#Run CI
if [ -z "$program_path" ]; then
	mkdir program
	cd program

	git clone $PROGRAM -b $PROGRAM_BRANCH
	url_arr+=($(echo $PROGRAM | grep -Po '[^\/]+'));
	repo=$(echo ${url_arr[3]} | grep -Po '^[^.]+');
	cd $repo;
	echo "$PWD" > $program_file	
else
	cd $program_path
	echo $PWD
fi

program_path=$(sed '1q;d' $program_file)

url_prog_arr+=($(echo $PROGRAM | grep -Po '[^\/]+'))
org_prog=${url_prog_arr[2]}
repo_prog=$(echo ${url_prog_arr[3]} | grep -Po '^[^.]+')

#Merge program's PR if found
if [ $program_pr_set == true ]; then

	#Check Server PR status
	program_prs_get=$(curl -s -n "https://api.github.com/repos/$org_prog/$repo_prog/pulls?state=open");
	program_prs_number=$(echo $program_prs_get | grep -Po '"number":.*?[^\\],');
	program_arr+=( $(echo $program_prs_number | grep -Po '[0-9]*')) ;

	program_pr_found=false

	for i in "${program_arr[@]}"
	do
		if [ $i == $PROGRAM_PR ]; then
			program_pr_found=true;
			break
		fi
	done

	if [ $program_pr_found == true ]; then
	        git checkout .;
		git fetch origin +refs/pull/$PROGRAM_PR/merge;
		git checkout FETCH_HEAD;
		git pull --rebase origin $PROGRAM_BRANCH;
		
		echo "Program: Merging Done"
		echo ""
	fi	
fi

if [ -z "$at_path" ]; then
	mkdir at
	cd at

	git clone $AT -b $AT_BRANCH
	cd $repo_at
	echo "$PWD" > $at_file
	
else
	cd $at_path
	echo $PWD
fi

at_path=$(sed '1q;d' $at_file)

#Merge PR
if [ $at_pr_found == true ]; then
        git checkout .;
	git fetch origin +refs/pull/$AT_PR/merge;
	git checkout FETCH_HEAD;
	git pull --rebase origin $AT_BRANCH;
	
	echo "Testsuite: Merging Done"
	echo ""
fi

#Building
echo "Program: Building ..."
cd $program_path

if [ $PROGRAM_BUILD == true ]; then
    mvn clean install -DskipTests $ADDITIONAL_PARAMS
fi

program_pom=$(<pom.xml)
version=$(echo $program_pom | grep -Po '<version>[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*<\/version>');
version=$(echo $version | grep -Po '[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*');

if [ -z "$JBOSS_VERSION" ]; then
    export JBOSS_VERSION=$version
fi
if [ -z "$JBOSS_FOLDER" ]; then
    export JBOSS_FOLDER=$PWD/dist/target/$repo_prog-$JBOSS_VERSION/
fi

cd $at_path

echo "Testsuite: Building ..."

if ! [ -z "$GITHUB_TOKEN" ] && ! [ -z "$AT_PR" ]; then
	
	function comment {
		body=""
		if [ "$1" == true ]; then
	 		body+="Build Success for $TEST_CATEGORY version $JBOSS_VERSION" 
		else
			body+="Build Failed for $TEST_CATEGORY version $JBOSS_VERSION" 
		fi
		
		body+=", "$(date '+%Y-%m-%d %H:%M:%S')

		comment=$(
			curl -s --request POST "https://api.github.com/repos/$org_at/$repo_at/issues/$AT_PR/comments" \
			--header 'Content-Type: application/json' \
			--header 'Authorization: token '$GITHUB_TOKEN \
			--data '{"body": "'"$body"'"}'
			)

	}
	
	mvn clean install -D$TEST_CATEGORY -Dstandalone $ADDITIONAL_PARAMS
	
	#Maven return code
	if [ "$?" -eq 0 ] ; then
		#OK
		comment true
	else
		#NOT OK
		comment false 
	fi
else
	mvn clean install -D$TEST_CATEGORY -Dstandalone $ADDITIONAL_PARAMS
fi
