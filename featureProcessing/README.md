## How to add and remove features

Clone the application to be tested (if applicable)

export EAT_HOME="path to EAT dir"

export FEATURE_FILE="path the the file that describes the feature inclusion and deletion", e.g. https://github.com/EAT-JBCOMMUNITY/EAT/blob/master/featureProcessing/featureChoicesExample.txt

cd $EAT_HOME/featureProcessing

mvn clean install -DskipTests

mvn exec:java

Build the application (if applicable)

Run the testsuite



## An example

1. mvn clean install -Dandroid -DnoDistribution -Dclone (-Dmodule='android-project-name')
or gradle test -Pandroid -PnoDistribution -Pclone (-Pmodule='android-project-name') -c androidsettings.gradle
2. export ANDROID_SDK_VERSION=...e.g. 1.0.0
3. export JBOSS_VERSION=...e.g. 1.0.0 (used for version distribution)
4. export ANDROID_SDK_ROOT='path to the Sdk'
5. export ANDROID_HOME='path to the Sdk'
6. Add local.properties files at the android projects (modules/testcases/jdkAll/Android/'project'/'project-name') you want to build with entry sdk.dir='path to the Sdk'
7. Apply the feature selection from previous section
8.  mvn clean install (-DnoDistribution) -Dandroidversion -Dandroid -Dcreate (-Dmodule='android-project-name') or gradle test (-PnoDistribution) -Pandroidversion -Pandroid -Pcreate (-Pmodule='android-project-name') -c androidsettings.gradle
9.  Start the emulator (in case of orchestrated tests) : $ANDROID_SDK_ROOT/emulator/emulator -avd $EMULATOR
10.  mvn clean install -DnoDistribution -Dandroidversion -Dandroid -Dtest (-Dmodule='android-project-name') or gradle test -PnoDistribution -Pandroidversion -Pandroid -Ptest (-Pmodule='android-project-name') -c androidsettings.gradle
