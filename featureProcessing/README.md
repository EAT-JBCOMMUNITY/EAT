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
3. export ANDROID_SDK_ROOT='path to the Sdk'
4. export ANDROID_HOME='path to the Sdk'
5. Add local.properties files at the android projects (modules/testcases/jdkAll/Android/'project'/'project-name') you want to build with entry sdk.dir='path to the Sdk'
6. Apply the feature selection from previous section
7.  mvn clean install (-DnoDistribution) -Dandroidversion -Dandroid -Dcreate (-Dmodule='android-project-name') or gradle test (-PnoDistribution) -Pandroidversion -Pandroid -Pcreate (-Pmodule='android-project-name') -c androidsettings.gradle
8.  Start the emulator (in case of orchestrated tests) : $ANDROID_SDK_ROOT/emulator/emulator -avd $EMULATOR
9.  mvn clean install -DnoDistribution -Dandroidversion -Dandroid -Dtest (-Dmodule='android-project-name') or gradle test -PnoDistribution -Pandroidversion -Pandroid -Ptest (-Pmodule='android-project-name') -c androidsettings.gradle


Version 1

1. mvn clean install -Dandroid -DnoDistribution -Dclone -Dmodule=testorchestratorwithtestcoveragesample
2. export ANDROID_SDK_VERSION=1.0.0
3. export ANDROID_HOME=~/Android/Sdk
4. export ANDROID_SDK_ROOT=~/Android/Sdk
5. $ANDROID_SDK_ROOT/emulator -avd Pixel_API_X (in another terminal)
6. mvn clean install -Dandroidversion -Dandroid -Dcreate -Dmodule=testorchestratorwithtestcoveragesample
7. mvn clean install -Dandroidversion -Dandroid -Dtest -Dmodule=testorchestratorwithtestcoveragesample

Version 2

1. export ANDROID_SDK_VERSION=2.0.0
2. mvn clean install -Dandroidversion -Dandroid -Dcreate -Dmodule=testorchestratorwithtestcoveragesample
3. mvn clean install -Dandroidversion -Dandroid -Dtest -Dmodule=testorchestratorwithtestcoveragesample

Version 3

1. export ANDROID_SDK_VERSION=3.0.0
2. export EAT_HOME="path to EAT dir"
3. export FEATURE_FILE=$EAT_HOME/featureProcessing/featureChoicesExample.txt
4. cd $EAT_HOME/featureProcessing
5. mvn clean install -DskipTests
6. mvn exec:java
7. cd $EAT_HOME
8. mvn clean install -Dandroidversion -Dandroid -Dcreate -Dmodule=testorchestratorwithtestcoveragesample
9. mvn clean install -Dandroidversion -Dandroid -Dtest -Dmodule=testorchestratorwithtestcoveragesample


