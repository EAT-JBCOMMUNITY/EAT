#!/bin/bash

set -e

rm -f program_path.txt
rm -f at_path.txt

if [ "$1" == "-v" ]; then
	./v.sh
elif [ "$1" == "-clear" ]; then
	echo ""
	
	echo "Clearing the workspace from previous builds..."
	echo ""
	
	./clear.sh	
elif [ "$1" == "-wildfly" ]; then
	export PROGRAM="https://github.com/wildfly/wildfly"
	export AT="https://github.com/EAT-JBCOMMUNITY/EAT"
	export ADDITIONAL_PARAMS=-Denforcer.skip
	export PROGRAM_BRANCH="main"
	
	./v.sh
	echo ""
	
	echo "Testing: Latest Wildfly + EAT"
	echo ""
	
	./pr.sh
elif [ "$1" == "-wildfly-jakarta" ]; then
	export PROGRAM="https://github.com/wildfly/wildfly"
	export AT="https://github.com/EAT-JBCOMMUNITY/EAT"
	export ADDITIONAL_PARAMS=-Denforcer.skip
	export PROGRAM_BRANCH="main"
	export TEST_CATEGORY=wildfly-jakarta
	
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
        mkdir -p activemqdir
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
	cd ".."
	./pr.sh
elif [ "$1" == "-activemq-artemis" ]; then
        mkdir -p activemq-artemis
        cd activemq-artemis
	export PROGRAM="https://github.com/apache/activemq-artemis"
	export AT="https://github.com/panossot/ArtemisActivemqAT"
	wget https://raw.githubusercontent.com/apache/activemq-artemis/main/pom.xml
	version=$(grep -m1 '<version>' ./pom.xml | tail -n1);
	version=$(echo $version | grep -Po '>.*<\/');
        version=$(echo "${version:1:${#version}}");
        version=$(echo "${version:0:${#version}-2}");
	export ACTIVEMQ_BRANCH_VERSION=$version
	export PROGRAM_BRANCH="main"
	export TEST_CATEGORY=master
		
	echo ""
	
	echo "Testing: Latest Apache Artemis ActiveMQ + Artemis ActiveMQ AT"
	echo ""
	cd ".."
	./pr.sh
elif [ "$1" == "-jboss-threads" ]; then
        mkdir -p jboss-threads
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
	cd ".."
	./pr.sh
elif [ "$1" == "-jboss-modules" ]; then
        mkdir -p jboss-module
        cd jboss-module
	export PROGRAM="https://github.com/jboss-modules/jboss-modules"
	export AT="https://github.com/panossot/JBossModulesAT"
	export PROGRAM_BRANCH=1.x
	export ADDITIONAL_PARAMS=-Denforcer.skip
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
	cd ".."
	./pr.sh
elif [ "$1" == "-openliberty" ]; then
        mkdir -p openliberty
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
	echo ""
	cd ".."
	./pr.sh
elif [ "$1" == "-springboot" ]; then
        mkdir -p springboot
        cd springboot
	export PROGRAM="https://github.com/spring-projects/spring-boot"
	export AT="https://github.com/panossot/SpringBootAT"
	wget https://raw.githubusercontent.com/spring-projects/spring-boot/master/gradle.properties
	version=$(grep -m1 'version=' ./gradle.properties | tail -n1);
	version=$(echo $version | grep -Po '=.*');
        version=$(echo "${version:1:${#version}}");
        version=$(echo "${version:0:${#version}}");
	export SPRINGBOOT_BRANCH_VERSION=$version
	wget https://raw.githubusercontent.com/spring-projects/spring-boot/master/buildSrc/build.gradle
	version=$(grep -m1 'spring-core:' ./build.gradle | tail -n1);
	version=$(echo $version | grep -Po ':.*\"');
        version=$(echo "${version:13:${#version}}");
        version=$(echo "${version:0:${#version}-1}");
        export SPRINGFRAMEWORK_BRANCH_VERSION=$version
	
	export PROGRAM_BUILD=true
	export TEST_CATEGORY=master
	export ADDITIONAL_PARAMS=-Dmaven.repo.local=$HOME/.m2/repository # Please modify accordingly ...
	echo ""
	
	echo "Testing: Latest Spring Boot release + Spring Boot AT"
	echo "The needed dependencies should be available at the maven repo defined in ADDITIONAL_PARAMS environment variable"
	echo ""
	cd ".."
	./gradle-at.sh
else 
	./pr.sh
fi
