## How to add and remove features

Clone the application to be tested (if applicable)

export EAT_HOME="path to EAT dir"

export FEATURE_FILE="path the the file that describes the feature inclusion and deletion", e.g. https://github.com/EAT-JBCOMMUNITY/EAT/blob/master/featureProcessing/featureChoicesExample.txt

cd $EAT_HOME/featureProcessing

mvn clean install -DskipTests

mvn exec:java

Build the application (if applicable)

Run the testsuite
