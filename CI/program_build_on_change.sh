#!/bin/bash

#set -e

if [ -z "$PROGRAM" ]; then
    PROGRAM=https://github.com/wildfly/wildfly.git
fi

if [ -z "$PROGRAM_BRANCH" ]; then
    PROGRAM_BRANCH=master
fi

if [ -z "$SLEEP_TIME" ]; then
    SLEEP_TIME=10
fi

git clone $PROGRAM -b $PROGRAM_BRANCH dependencyBuild
cd dependencyBuild

 oldCommitNum=$(git rev-list HEAD --count)

while true
do
        git pull --rebase origin $PROGRAM_BRANCH;
        newCommitNum=$(git rev-list HEAD --count);
        
        if [ $oldCommitNum != $newCommitNum ]; then
            oldCommitNum=$newCommitNum
	    mvn clean install -DskipTests;
	fi
	
	sleep $SLEEP_TIME
	
done
