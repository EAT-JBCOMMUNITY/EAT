FROM docker.io/centos:centos7

MAINTAINER Panagiotis Sotiropoulos <psotirop@redhat.com>

LABEL description="A container for running EAT in isolation"


# Upgrading system
RUN yum -y upgrade
RUN yum -y install wget
RUN yum install -y git
RUN yum install -y sudo

# Downloading Java
RUN wget --no-check-certificate "https://www.dropbox.com/scl/fi/hb5h62c79no0gzl8hoi6e/jdk-11.0.22_linux-x64_bin.rpm?rlkey=wa4qtonq5siwinfoi95v8ucfk&st=x6nh0u2e&dl=0" -O /tmp/jdk-11.0.22_linux-x64_bin.rpm && cd /tmp && yum -y install findutils && rpm -Uhv jdk-11.0.22_linux-x64_bin.rpm

RUN rm /tmp/jdk-11.0.22_linux-x64_bin.rpm

RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000

ENV JAVA_HOME /usr/java/latest


ENV MAVEN_VERSION 3.6.1

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven


RUN mkdir eat && cd eat && git clone https://github.com/EAT-JBCOMMUNITY/EAT.git master

RUN mkdir -p /home/user/.m2 && mkdir -p /eap/dist


WORKDIR "/eat/master"




