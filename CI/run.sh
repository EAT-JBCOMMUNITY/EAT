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
elif [ "$1" == "-activemq-artemis" ]; then
        mkdir activemqdir
        cd activemqdir
	export PROGRAM="https://github.com/apache/activemq-artemis"
	export AT="https://github.com/panossot/ArtemisActivemqAT"
	wget https://raw.githubusercontent.com/apache/activemq-artemis/master/pom.xml
	version=$(grep -m1 '<version>' ./pom.xml | tail -n1);
	version=$(echo $version | grep -Po '>.*<\/');
        version=$(echo "${version:1:${#version}}");
        version=$(echo "${version:0:${#version}-2}");
	export ACTIVEMQ_BRANCH_VERSION=$version
	export TEST_CATEGORY=master
	
	echo ""
	
	echo "Building: Latest Apache Artemis ActiveMQ + Artemis ActiveMQ AT"
	echo ""
	cd ..
	./pr.sh
elif [ "$1" == "-jboss-threads" ]; then
        mkdir activemqdir
        cd activemqdir
	export PROGRAM="https://github.com/jbossas/jboss-threads"
	export AT="https://github.com/panossot/JBTAT"
	wget https://raw.githubusercontent.com/jbossas/jboss-threads/master/pom.xml
	version=$(grep -m1 '<version>' ./pom.xml | tail -n1);
	version=$(echo $version | grep -Po '>.*<\/');
        version=$(echo "${version:1:${#version}}");
        version=$(echo "${version:0:${#version}-2}");
	export JBOSS_VERSION=$version
	export TEST_CATEGORY=master
	
	echo ""
	
	echo "Building: Latest JBoss Threads + JBoss Threads AT"
	echo "JDK 9 should be used"
	echo ""
	cd ..
	./pr.sh
elif [ "$1" == "-jboss-modules" ]; then
        mkdir activemqdir
        cd activemqdir
	export PROGRAM="https://github.com/jboss-modules/jboss-modules"
	export AT="https://github.com/panossot/JBossModulesAT"
	export PROGRAM_BRANCH=1.x
	wget https://raw.githubusercontent.com/jboss-modules/jboss-modules/$PROGRAM_BRANCH/pom.xml
	version=$(grep -m1 '<version>' ./pom.xml | tail -n1);
	version=$(echo $version | grep -Po '>.*<\/');
        version=$(echo "${version:1:${#version}}");
        version=$(echo "${version:0:${#version}-2}");
	export JBOSS_VERSION=$version
	export TEST_CATEGORY=1.x
	
	echo ""
	
	echo "Building: Latest JBoss Modules + JBoss Modules AT"
	echo "JDK 9 should be used"
	echo ""
	cd ..
	./pr.sh
else 
	./pr.sh
fi
