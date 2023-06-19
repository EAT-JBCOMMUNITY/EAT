# @EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/test-configurations/exec","modules/testcases/jdkAll/Eap7Plus/ejb/test-configurations/exec"}) 

pwd
cd exec
mvn exec:java -Denforcer.skip &> output.txt
