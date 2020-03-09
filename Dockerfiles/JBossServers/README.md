# RUN EAT WITH DOCKER IMAGES
-----------------------------

In order for run EAT using docker to test EAP Server Images, please, follow the next steps (after you modify settings-eateap.xml in order to contain the needed repositories) ...
1. Create the image for the EAP Server : e.g. docker build --build-arg GITHUB_USERNAME='github username' --build-arg GITHUB_PASSWORD='github password' --build-arg EAP_BRANCH='branch of EAP' -t docker.io/eapimage -f DockerfileEapImage .
2. Run the container attaching two volumes : e.g. docker run --name=eapserver -v DataVolume1:/eap/master -v DataVolume2:/home/user/.m2 docker.io/eapimage
3. Create the image for EAT : e.g. docker build -t  docker.io/eateap  -f DockerfileEat .
4. Run the container with the same two volumes attached, specifying the server version and the path to the server distribution as well as the EAT server code : e.g. docker run --name=eateap -v DataVolume1:/eap/master -v DataVolume2:/home/user/.m2 -e JBOSS_VERSION='EAP server version' -e JBOSS_FOLDER='EAP server distribution path' -e EATCODE='EAT server code' docker.io/eateap 




# RUN EAT with OPENSHIFT (Additional instructions should be added relevant to the ones above)
---------------------------------------------------------------------------------------------


1. Use Dockerfile in DockerfileLayer1 dir to create image docker.io/eateap : docker build -t docker.io/eateap .
2. Add in the container of this image the eap dist and the maven repo artifacts that are needed for EAT 
3. Commit the changes
4. Use Dockerfile in DockerfileLayer2 dir to create image docker.io/eat:eap7x : docker build -t docker.io/eat:eap7x .
5. Push the tagged image to some repo : docker push docker.io/jboss/eat:eap7x
6. In Openshift create a project : e.g. eat
7. Get started with your project using 'Deploy Image'. e.g. choose image name and refer to the image uploaded at the repo in the previous step
8. Set the environment variables JBOSS_VERSION='the version of the server' , JBOSS_FOLDER=/eap/dist/target/jboss-eap-'the version of the server' and SERVER_CODE='code of the server used with eat, eg eap72x'
9. Push the Deploy button and check EAT EAP running in Openshift (e.g. using the Logs)

