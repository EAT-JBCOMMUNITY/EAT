#!/bin/bash

set -e

#Parameters
WILDFLY=$WILDFLY
EAT=$EAT
EAT_PR=$EAT_PR

if [ "$1" == "-v" ]; then
	echo "Wildfly: "$WILDFLY
	echo "EAT:     "$EAT
	echo "EAT_PR:  "$EAT_PR
	exit 0
fi

if [ -z "$WILDFLY" ]; then
	echo "Define Wildlfy server (github)"
	echo ""
	echo "Example"
	echo "export WILDFLY=..."
	exit 1;
fi

if [ -z "$EAT" ]; then
	echo "Define EAT (github)"
	echo ""
	echo "Example"
	echo "export EAT=..."
	exit 1;
fi

if [ -z "$EAT_PR" ]; then
	echo "Define Pull Request number"
	echo ""
	echo "Example"
	echo "export EAT_PR=1"
	exit 1;
fi

#Check PR status
prs=$(curl -s -n https://api.github.com/repos/EAT-JBCOMMUNITY/EAT/pulls?state=open);
aa=$(echo $prs | grep -Po '"number":.*?[^\\],');
arr+=( $(echo $aa | grep -Po '[0-9]*')) ;

pr_found=false

for i in "${arr[@]}"
do
	if [ $i == $EAT_PR ]; then
		pr_found=true;
		break
	fi
done

if [ $pr_found == false ]; then
	echo "Pull Request not found."
fi

#Run CI
mkdir server
cd server

git clone $WILDFLY
cd wildfly

mkdir eat
cd eat

git clone $EAT
cd EAT

#Merge PR
git fetch origin +refs/pull/$EAT_PR/merge;
git checkout FETCH_HEAD;

echo "Merging Done!"
echo ""
# echo "Starting Build"
