FROM docker.io/centos:centos7

LABEL description="A container for testing EAT PR in isolation"

ADD ./* /home/user/

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

# Add JDK bin dir to PATH
ENV PATH=$PATH:/usr/java/latest/bin/

ENV MAVEN_VERSION 3.9.6

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

ENV PATH=$PATH:/usr/share/maven/bin/

RUN cd /home/user && ./clear.sh

WORKDIR "/home/user"

ENV AT_PR=$AT_PR 
ENV PROGRAM_PR=$PROGRAM_PR
ENV PROGRAM=$PROGRAM
ENV PROGRAM_BUILD=$PROGRAM_BUILD
ENV AT=$AT
ENV GITHUB_TOKEN=$GITHUB_TOKEN
ENV TEST_CATEGORY=$TEST_CATEGORY
ENV PROGRAM_BRANCH=$PROGRAM_BRANCH
ENV TEST_PROGRAM=$TEST_PROGRAM
ENV JBOSS_FOLDER=$JBOSS_FOLDER
ENV JBOSS_VERSION=$JBOSS_VERSION
ENV ADDITIONAL_PARAMS="-s /home/user/settings-at.xml"

CMD ./run.sh -$TEST_PROGRAM




