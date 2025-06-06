FROM docker.io/fedora

LABEL description="A container for running EAT in isolation"

# Upgrading system
RUN dnf -y upgrade
RUN dnf install -y git
RUN dnf install -y sudo

# Downloading Java
RUN curl -O https://download.java.net/java/GA/jdk11/13/GPL/openjdk-11.0.1_linux-x64_bin.tar.gz && tar zxvf openjdk-11.0.1_linux-x64_bin.tar.gz && mv jdk-11* /usr/local/

ENV JAVA_HOME /usr/local/jdk-11.0.1

# Add JDK bin dir to PATH
ENV PATH=$PATH:$JAVA_HOME/bin/
ENV MAVEN_VERSION 3.6.3

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

ENV PATH=$PATH:/usr/share/maven/bin/

RUN mkdir -p /home/user/.m2/repository

ADD repository /home/user/.m2/repository
ADD ./settings-eateap.xml /usr/share

RUN mkdir eap7 && cd eap7 && git clone https://$GITHUB_USERNAME:$GITHUB_PASSWORD@github.com/jbossas/jboss-eap7.git --branch 7.x && cd jboss-eap7 && ls -la && sed -i 's/<!-- keep this repository the first -->/<repository><id>redhat-ga<\/id><name>Redhat GA Repository Group<\/name><url>https:\/\/maven\.repository\.redhat\.com\/ga\/<\/url><layout>default<\/layout><releases><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/releases><snapshots><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/snapshots><\/repository><repository><id>redhat-eng<\/id><name>Redhat eng Repository Group<\/name><url>https:\/\/download-node-02.eng.bos.redhat.com\/brewroot\/repos\/jb-eap-7.3-maven-build\/latest\/maven\/<\/url><layout>default<\/layout><releases><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/releases><snapshots><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/snapshots><\/repository><repository><id>redhat-eng2<\/id><name>Redhat eng2 Repository Group<\/name><url>http:\/\/download.eng.bne.redhat.com\/brewroot\/repos\/jb-eap-7.3-maven-build\/latest\/maven\/<\/url><layout>default<\/layout><releases><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/releases><snapshots><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/snapshots><\/repository><repository><id>dev<\/id><name>dev Repository Group<\/name><url>https:\/\/repository.jboss.org\/nexus\/content\/groups\/developer\/<\/url><layout>default<\/layout><releases><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/releases><snapshots><enabled>true<\/enabled><updatePolicy>never<\/updatePolicy><\/snapshots><\/repository>/g' ./pom.xml && mvn clean install -DskipTests -Dmaven.repository.protocol=http -Denforcer.skip -s "/usr/share/settings-eateap.xml" && JBOSS_VERSION="$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version|grep -Ev '(^\[|Download\w+:)')" && export JBOSS_VERSION=$JBOSS_VERSION && JBOSS_FOLDER="$(pwd)/dist/target/jboss-eap-7.3" && export JBOSS_FOLDER=$JBOSS_FOLDER && mkdir EAT && cd EAT && git clone https://github.com/EAT-JBCOMMUNITY/EAT.git master && cd master && mvn clean install -Deap7+ -Dstandalone -Dmaven.repository.protocol=http -Denforcer.skip -s "/usr/share/settings-eateap.xml"









