#!/bin/bash

set -e

printVariables() {
	echo "SERVER:        "$SERVER
	echo "SERVER_PR:     "$SERVER_PR
	echo "EAT:           "$EAT
	echo "EAT_PR:        "$EAT_PR
}

#Parameters
SERVER=$SERVER
EAT=$EAT
EAT_PR=$EAT_PR
SERVER_PR=$SERVER_PR

if [ -z "$TEST_CATEGORY" ]; then
    TEST_CATEGORY=wildfly
fi

server_pr_set=false

if [ -z "$SERVER_BUILD" ]; then
    SERVER_BUILD=true
fi

if [ -z "$EAT_BRANCH" ]; then
    EAT_BRANCH=master
fi

if [ -z "$SERVER_BRANCH" ]; then
    SERVER_BRANCH=master
fi

eat_file="eat_path.txt"
server_file="server_path.txt"

if ! [ -r $server_file ]; then
	>> $server_file
fi
server_path=$(sed '1q;d' $server_file)

if ! [ -r $eat_file ]; then
	>> $eat_file
fi
eat_path=$(sed '1q;d' $eat_file)

eat_file=$(realpath $eat_file)
server_file=$(realpath $server_file)

if [ "$1" == "-v" ]; then
	printVariables
	exit 0
fi

if [ "$1" == "-wildfly" ]; then
	SERVER="https://github.com/wildfly/wildfly"
	EAT="https://github.com/EAT-JBCOMMUNITY/EAT"
	
	printVariables
	echo ""
	
	echo "Building: Latest Wildfly + EAT"
	echo ""
fi

if [ -z "$SERVER" ]; then
	echo "Define server (github)"
	echo ""
	echo "Example"
	echo "export SERVER=..."
	exit 1;
fi

if [ -z "$EAT" ]; then
	echo "Define EAT (github)"
	echo ""
	echo "Example"
	echo "export EAT=..."
	exit 1;
fi

if ! [ -z "$SERVER_PR" ] && [ "$SERVER_PR" -gt 0 ]; then
	server_pr_set=true
fi

#Check EAT PR status
eat_prs_get=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls?state=open);
eat_prs_number=$(echo $eat_prs_get | grep -Po '"number":.*?[^\\],');
eat_arr+=( $(echo $eat_prs_number | grep -Po '[0-9]*')) ;

eat_pr_found=false

if [ $EAT_PR != "ALL" ] && [ $EAT_PR != "all" ]; then
	for i in "${eat_arr[@]}"
	do
		if [ $i == $EAT_PR ]; then
			eat_pr_found=true;
			break
		fi
	done

	if [ $eat_pr_found == false ]; then
		echo "Pull Request not found."
	fi
fi

#Run CI
if [ -z "$server_path" ]; then
	mkdir server
	cd server

	git clone $SERVER -b $SERVER_BRANCH
	cd *
	echo "$PWD" > $server_file	
else
	cd $server_path
	echo $PWD
fi

server_path=$(sed '1q;d' $server_file)

#Merge server's PR if found
if [ $server_pr_set == true ]; then

	#Check Server PR status
	server_prs_get=$(curl -s -n https://api.github.com/repos/wildfly/wildfly/pulls?state=open);
	server_prs_number=$(echo $server_prs_get | grep -Po '"number":.*?[^\\],');
	server_arr+=( $(echo $server_prs_number | grep -Po '[0-9]*')) ;

	server_pr_found=false

	for i in "${server_arr[@]}"
	do
		if [ $i == $SERVER_PR ]; then
			server_pr_found=true;
			break
		fi
	done

	if [ $server_pr_found == true ]; then
	        git checkout .;
		git fetch origin +refs/pull/$SERVER_PR/merge;
		git checkout FETCH_HEAD;
		git pull --rebase origin $SERVER_BRANCH;
		
		echo "Server: Merging Done"
		echo ""
	fi	
fi

if [ -z "$eat_path" ]; then
	mkdir eat
	cd eat

	git clone $EAT -b $EAT_BRANCH
	cd EAT
	echo "$PWD" > $eat_file
	
else
	cd $eat_path
	echo $PWD
fi

eat_path=$(sed '1q;d' $eat_file)

#Merge PR
if [ $EAT_PR != "ALL" ] && [ $EAT_PR != "all" ] && [ $eat_pr_found != false ]; then
        git checkout .;
	git fetch origin +refs/pull/$EAT_PR/merge;
	git checkout FETCH_HEAD;
	git pull --rebase origin $EAT_BRANCH;
	
	echo "EAT: Merging Done"
	echo ""
fi

#Build Server
echo "Building..."
cd $server_path

if [ $SERVER_BUILD == true ]; then
    mvn clean install -DskipTests
fi

server_pom=$(<pom.xml)
version=$(echo $server_pom | grep -Po '<version>[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*<\/version>');
version=$(echo $version | grep -Po '[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*');

if [ -z "$JBOSS_VERSION" ]; then
    export JBOSS_VERSION=$version
fi
if [ -z "$JBOSS_FOLDER" ]; then
    export JBOSS_FOLDER=$PWD/dist/target/wildfly-$JBOSS_VERSION/
fi

cd $eat_path

if [ $EAT_PR == "ALL" ] || [ $EAT_PR == "all" ]; then

	checked_prs_file="checked_PRs.txt"
	
	if ! [ -r $checked_prs_file ]; then
		>> $checked_prs_file
	fi
	
	#Read file lines to array
	mapfile -t checked_arr < checked_PRs.txt
	
	for i in "${eat_arr[@]}"
	do
		checked=false
		
		for j in "${checked_arr[@]}"
		do
			if [ $i == $j ]; then
				checked=true
				break
			fi
		done
		
		if [ $checked == true ]; then
			continue
		fi
		
		git checkout .;
		git fetch origin +refs/pull/$i/merge;
		git checkout FETCH_HEAD;
		git pull --rebase origin $EAT_BRANCH;
		
		echo "EAT: Merging Done!"
		echo ""
		
		mvn clean install -D$TEST_CATEGORY -Dstandalone
		
		echo $i >> checked_PRs.txt
	done
else
	mvn clean install -D$TEST_CATEGORY -Dstandalone
fi
