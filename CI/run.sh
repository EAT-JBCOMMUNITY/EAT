#!/bin/bash

set -e

if [ "$1" == "-v" ]; then
	./v.sh
	
elif [ "$1" == "-wildfly" ]; then
	export PROGRAM="https://github.com/wildfly/wildfly"
	export AT="https://github.com/EAT-JBCOMMUNITY/EAT"
	
	./v.sh
	echo ""
	
	echo "Building: Latest Wildfly + EAT"
	echo ""
	
	./pr.sh
elif [ "$1" == "-all" ]; then
	./all.sh $2 $3
elif [ "$1" == "-at" ]; then
	./at.sh
elif [ "$1" == "-activemq" ]; then
        mkdir activemqdir
        cd activemqdir
	export PROGRAM="https://github.com/apache/activemq"
	export AT="https://github.com/panossot/ActivemqAT"
	wget https://raw.githubusercontent.com/apache/activemq/master/pom.xml
	version=$(grep -m2 '<version>' ./pom.xml | tail -n1);
	version=$(echo $version | grep -Po '>.*<\/');
        version=$(echo "${version:1:${#version}}");
        version=$(echo "${version:0:${#version}-2}");
	export ACTIVEMQ_BRANCH_VERSION=$version
	export TEST_CATEGORY=master
	
	echo ""
	
	echo "Building: Latest Apache ActiveMQ + ActiveMQ AT"
	echo ""
	cd ..
	./pr.sh
else 
	./pr.sh
fi
