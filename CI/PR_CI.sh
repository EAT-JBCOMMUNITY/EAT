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
prs=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls?state=open);
aa=$(echo $prs | grep -Po '"number":.*?[^\\],');
arr+=( $(echo $aa | grep -Po '[0-9]*')) ;

eat_pr_found=false

for i in "${arr[@]}"
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

#Run CI
mkdir server
cd server

git clone $SERVER
cd *

#Merge server's PR if found
if [ $server_pr_set == true ]; then
	git fetch origin +refs/pull/$SERVER_PR/merge;
	git checkout FETCH_HEAD;
fi

mkdir eat
cd eat

git clone $EAT
cd EAT

#Merge PR
git fetch origin +refs/pull/$EAT_PR/merge;
git checkout FETCH_HEAD;

echo "Merging Done!"
echo ""

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

mvn clean install -Dwildfly -Dstandalone
