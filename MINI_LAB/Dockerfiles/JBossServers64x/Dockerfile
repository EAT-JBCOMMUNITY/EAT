FROM docker.io/centos:centos7

MAINTAINER Panagiotis Sotiropoulos <psotirop@redhat.com>

LABEL description="A container for running EAT in isolation"

# Upgrading system
RUN yum -y upgrade
RUN yum -y install wget
RUN yum install -y git
RUN yum install -y sudo

# Downloading Java
RUN wget --no-check-certificate "https://www.dropbox.com/s/vm819mraxwidxqh/jdk-8u191-linux-x64.rpm?dl=0" -O /tmp/jdk-8-linux-x64.rpm && cd /tmp && yum -y --allowerasing install findutils && rpm -Uhv jdk-8-linux-x64.rpm

RUN rm /tmp/jdk-8-linux-x64.rpm 

RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000

ENV JAVA_HOME /usr/java/latest

# Add JDK bin dir to PATH
ENV PATH=$PATH:/usr/java/latest/bin/

ENV MAVEN_VERSION 3.3.9

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

ENV PATH=$PATH:/usr/share/maven/bin/

ADD repository /home/user/.m2/repository

RUN mkdir eap6 && cd eap6 && git clone https://$GITHUB_USERNAME:$GITHUB_PASSWORD@github.com/jbossas/jboss-eap.git --branch 6.4.x && cd jboss-eap && ls -la && mvn clean install -Dmaven.repository.protocol=http && export JBOSS_VERSION=$(ls -d build/target/jboss-as-* | sed -e 's/^.*jboss-as-//' | head -1) && export JBOSS_FOLDER="$(pwd)/${JBOSS_CODE}/build/target/jboss-as-${JBOSS_VERSION}" && mkdir EAT && cd EAT && git clone https://github.com/EAT-JBCOMMUNITY/EAT.git master && cd master && mvn clean install -Deap64x -Dstandalone -Dmaven.repository.protocol=http




