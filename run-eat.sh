#!/bin/bash

export M2_HOME=${MAVEN_HOME:-'/home/rpelisse/Products/tools/apache-maven-3.3.9/'}
export PATH=$M2_HOME/bin:$PATH
export MAVEN_OPTS="$MAVEN_OPTS -Dmaven.wagon.http.pool=false"
export MAVEN_OPTS="$MAVEN_OPTS -Dmaven.wagon.httpconnectionManager.maxPerRoute=3"
readonly SETTINGS_XML=$(pwd)/settings.xml
export SETTINGS_XML

rm -rf wildfly
git clone git@github.com:wildfly/wildfly.git --branch master
mkdir -p maven_local_repo
cd ./wildfly
export JBOSS_VERSION="$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version|grep -Ev '(^\[|Download\w+:)')"
mvn clean install -DskipTests -Dmaven.repo.local=./../maven_local_repo -s "${SETTINGS_XML}"
cd ..
export LOCAL_REPO_DIR=${PWD}/maven_local_repo
export JBOSS_VERSION_CODE=wildfly
export JBOSS_FOLDER=${PWD}/wildfly/dist/target/wildfly-${JBOSS_VERSION}
bash -x ./jenkins-job.sh || exit 0
