rm -r -f wildfly
git clone https://github.com/panossot/wildfly.git
cd wildfly
mvn install -DskipTests
