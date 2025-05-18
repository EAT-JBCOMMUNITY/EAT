FROM docker.io/fedora

LABEL description="A container for running EAT in isolation"

ADD ./settings-eatwildfly.xml /usr/share

# Upgrading system
RUN dnf -y upgrade
RUN dnf install -y git
RUN dnf install -y sudo

# Downloading Java
RUN curl -O https://download.oracle.com/java/17/archive/jdk-17.0.12_linux-x64_bin.tar.gz && tar zxvf jdk-17.0.12_linux-x64_bin.tar.gz && mv jdk-17* /usr/local/

ENV JAVA_HOME /usr/local/jdk-17.0.12

# Add JDK bin dir to PATH
ENV PATH=$PATH:$JAVA_HOME/bin/

ENV MAVEN_VERSION 3.9.6

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

ENV PATH=$PATH:/usr/share/maven/bin/

ADD repository /home/user/.m2/repository

RUN mkdir wildfly && cd wildfly && git clone https://github.com/wildfly/wildfly.git master && cd master && ls -la && mvn clean install -DskipTests -Denforcer.skip -s "/usr/share/settings-eatwildfly.xml" && tmp=$(ls -d $(pwd)/ee-dist/target/wildfly-* | sed -e '/.jar/d') && JBOSS_VERSION=$(basename "${tmp}" | sed -e 's/wildfly-//' | sed -e 's/-for-validation//') && export JBOSS_VERSION=$JBOSS_VERSION && JBOSS_FOLDER="$(pwd)/ee-dist/target/wildfly-${JBOSS_VERSION}" && export JBOSS_FOLDER=$JBOSS_FOLDER  && mkdir EAT && cd EAT && git clone https://github.com/EAT-JBCOMMUNITY/EAT.git master && cd master && mvn clean install -Dwildfly-jakarta -Dstandalone -DJBOSS_VERSION=$JBOSS_VERSION -DJBOSS_FOLDER=$JBOSS_FOLDER -Denforcer.skip -s "/usr/share/settings-eatwildfly.xml"



