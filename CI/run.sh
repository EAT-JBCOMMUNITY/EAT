#!/bin/bash

set -e

if [ "$1" == "-v" ]; then
	./v.sh
	
elif [ "$1" == "-wildfly" ]; then
	export PROGRAM="https://github.com/wildfly/wildfly"
	export AT="https://github.com/EAT-JBCOMMUNITY/EAT"
	
	./v.sh
	echo ""
	
	echo "Testing: Latest Wildfly + EAT"
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
	
	echo "Testing: Latest Apache ActiveMQ + ActiveMQ AT"
	echo ""
	cd ..
	./pr.sh
elif [ "$1" == "-activemq-artemis" ]; then
        mkdir activemq-artemis
        cd activemq-artemis
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
	
	echo "Testing: Latest Apache Artemis ActiveMQ + Artemis ActiveMQ AT"
	echo ""
	cd ..
	./pr.sh
elif [ "$1" == "-jboss-threads" ]; then
        mkdir jboss-threads
        cd jboss-threads
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
	
	echo "Testing: Latest JBoss Threads + JBoss Threads AT"
	echo "JDK 9 should be used"
	echo ""
	cd ..
	./pr.sh
elif [ "$1" == "-jboss-modules" ]; then
        mkdir jboss-module
        cd jboss-module
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
	
	echo "Testing: Latest JBoss Modules + JBoss Modules AT"
	echo "JDK 9 should be used"
	echo ""
	cd ..
	./pr.sh
elif [ "$1" == "-openliberty" ]; then
        mkdir openliberty
        cd openliberty
	export PROGRAM="https://github.com/OpenLiberty/open-liberty"
	export AT="https://github.com/panossot/OAT"
	wget https://raw.githubusercontent.com/panossot/OAT/master/features.txt
	export FEATURE_LIST=$PWD/features.txt
	export PROGRAM_BUILD=false
	export OPENLIBERTY_VERSION=RELEASE
	export TEST_CATEGORY=openliberty
	
	echo ""
	
	echo "Testing: Latest Open Liberty release + Open Liberty AT"
	echo "JDK 9 should be used"
	echo ""
	cd ..
	./pr.sh
else 
	./pr.sh
fi
