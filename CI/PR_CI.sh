#!/bin/bash

set -e

#Parameters
SERVER=$SERVER
EAT=$EAT
EAT_PR=$EAT_PR
SERVER_PR=$SERVER_PR

server_pr_set=false

if [ "$1" == "-v" ]; then
	echo "SERVER:        "$SERVER
	echo "EAT:           "$EAT
	echo "EAT_PR:        "$EAT_PR
	exit 0
fi

if [ "$1" == "-wildfly" ]; then
	if [ -z "$EAT_PR" ]; then
		echo "Define Pull Request number"
		echo ""
		echo "Example"
		echo "export EAT_PR=1"
		exit 1;
	fi
	
	SERVER="https://github.com/wildfly/wildfly"
	EAT="https://github.com/EAT-JBCOMMUNITY/EAT"
	
	echo "SERVER:        "$SERVER
	echo "EAT:           "$EAT
	echo "EAT_PR:        "$EAT_PR
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

if [ -z "$SERVER_PR" ]; then
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
		exit 1;
	fi
fi

#Run CI
mkdir server
cd server

git clone $SERVER
cd *

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
		git fetch origin +refs/pull/$SERVER_PR/merge;
		git checkout FETCH_HEAD;
	fi	
fi

mkdir eat
cd eat

git clone $EAT
cd EAT

#Merge PR
if [ $EAT_PR != "ALL" ] && [ $EAT_PR != "all" ]; then
	git fetch origin +refs/pull/$EAT_PR/merge;
	git checkout FETCH_HEAD;
	
	echo "Merging Done!"
	echo ""
fi

#Build Server
echo "Building..."
cd ../../
mvn clean install -DskipTests

server_pom=$(<pom.xml)
version=$(echo $server_pom | grep -Po '<version>[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*<\/version>');
version=$(echo $version | grep -Po '[0-9]*\.[0-9]*\.[0-9]*\.[a-zA-Z0-9-]*');

export JBOSS_VERSION=$version
export JBOSS_FOLDER=$PWD/dist/target/wildfly-$JBOSS_VERSION/

cd eat
cd EAT

if [ $EAT_PR == "ALL" ] || [ $EAT_PR == "all" ]; then

	for i in "${eat_arr[@]}"
	do
		git fetch origin +refs/pull/$i/merge;
		git checkout FETCH_HEAD;
		
		echo "Merging Done!"
		echo ""
		
		mvn clean install -Dwildfly -Dstandalone
	done
else
	mvn clean install -Dwildfly -Dstandalone
fi
