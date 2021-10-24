FROM docker.io/fedora

MAINTAINER Panagiotis Sotiropoulos <psotirop@redhat.com>

LABEL description="A container for running EAT in isolation"

# Upgrading system
RUN dnf -y upgrade
RUN dnf -y install wget
RUN dnf install -y git
RUN dnf install -y sudo

# Downloading Java
RUN curl -O https://download.java.net/java/GA/jdk11/13/GPL/openjdk-11.0.1_linux-x64_bin.tar.gz && tar zxvf openjdk-11.0.1_linux-x64_bin.tar.gz && mv jdk-11* /usr/local/

ENV JAVA_HOME /usr/local/jdk-11.0.1

# Add JDK bin dir to PATH
ENV PATH=$PATH:$JAVA_HOME/bin/

ENV MAVEN_VERSION 3.5.3

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

ENV PATH=$PATH:/usr/share/maven/bin/

ENV GLIBC_VERSION=2.27-r0


RUN dnf -y install gcc && dnf -y install zlib-devel && dnf -y install glibc-devel.i686 glibc.i686

ENV GRAAL_VERSION 21.0.0.2
ENV GRAAL_HOME /usr/lib/jvm/vm-$GRAAL_VERSION
ENV PATH $GRAAL_HOME/graalvm-ce-java8-$GRAAL_VERSION/bin:$PATH

RUN mkdir -p $GRAAL_HOME &&  wget --no-check-certificate https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-$GRAAL_VERSION/graalvm-ce-java8-linux-amd64-$GRAAL_VERSION.tar.gz -O $GRAAL_HOME/graalvm-ce-$GRAAL_VERSION-linux-amd64.tar 


RUN tar xvf $GRAAL_HOME/graalvm-ce-$GRAAL_VERSION-linux-amd64.tar -C $GRAAL_HOME \
     && rm "$GRAAL_HOME/graalvm-ce-$GRAAL_VERSION-linux-amd64.tar" && ls -la $GRAAL_HOME/graalvm-ce-java8-$GRAAL_VERSION/bin && cd $GRAAL_HOME/graalvm-ce-java8-$GRAAL_VERSION/bin && sudo ./gu install native-image

ENV GRAALVM_HOME $GRAAL_HOME/graalvm-ce-java8-$GRAAL_VERSION

ADD repository /home/user/.m2/repository

RUN mkdir protean && cd protean && git clone https://github.com/quarkusio/quarkus.git master && cd master && ls -la && export MAVEN_OPTS="-Xmx3000m" && ./mvnw clean install -DskipTests -DskipITs -DskipDocs && export QUARKUS_VERSION=999-SNAPSHOT &&  JBOSS_VERSION=999.0.0-SNAPSHOT && export JBOSS_VERSION=$JBOSS_VERSION && mkdir EAT && cd EAT && git clone https://github.com/EAT-JBCOMMUNITY/EAT.git && cd EAT && export NATIVE=-Dnative && ./run-quarkus.sh
