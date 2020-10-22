FROM docker.io/centos

MAINTAINER Panagiotis Sotiropoulos <psotirop@redhat.com>

LABEL description="A container for testing EAT PR in isolation"

ADD ./* /home/user/

# Upgrading system
RUN dnf -y upgrade
RUN dnf -y install wget
RUN dnf install -y git
RUN dnf install -y sudo

# Downloading Java
RUN wget --no-check-certificate "https://www.dropbox.com/s/vm819mraxwidxqh/jdk-8u191-linux-x64.rpm?dl=0" -O /tmp/jdk-8-linux-x64.rpm && cd /tmp && dnf -y --allowerasing install findutils && rpm -Uhv jdk-8-linux-x64.rpm

RUN rm /tmp/jdk-8-linux-x64.rpm 

RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000


ENV JAVA_HOME /usr/java/latest

# Add JDK bin dir to PATH
ENV PATH=$PATH:/usr/java/latest/bin/

ENV MAVEN_VERSION 3.6.3

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

ENV PATH=$PATH:/usr/share/maven/bin/

RUN cd /home/user && ./clear.sh

WORKDIR "/home/user"

ENV AT_PR=$AT_PR 
ENV PROGRAM_PR=$PROGRAM_PR
ENV GITHUB_TOKEN=$GITHUB_TOKEN
ENV TEST_CATEGORY=$TEST_CATEGORY
ENV PROGRAM_BRANCH=$PROGRAM_BRANCH
ENV TEST_PROGRAM=$
ENV ADDITIONAL_PARAMS="-s /home/user/settings-at.xml"

CMD ./run.sh -$TEST_PROGRAM




