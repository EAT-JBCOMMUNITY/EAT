# RUN EAT WITH DOCKER IMAGES
-----------------------------

In order for run EAT using docker to test Wildfly Server Images, please, follow the next steps ...

1. Create the image for the Wildfly Server : e.g. docker build -t docker.io/wildflyserver -f DockerfileWildflyImage .
2. Run the container attaching two volumes : e.g. docker run --name=ywildflyserver -v DataVolume1:/wildfly/master -v DataVolume2:/home/user/.m2 docker.io/wildflyserver
3. Stop the server container if needed.
4. Create the image for EAT : e.g. docker build -t docker.io/wildflyeat -f DockerfileEat .
5. Run the container with the same two volumes attached, specifying the server version and the path to the server distribution : e.g. docker run --name=wildflyeat -v DataVolume1:/wildfly/master -v DataVolume2:/home/user/.m2 -e JBOSS_FOLDER=/wildfly/master/dist/target/wildfly-19.0.0.Beta1-SNAPSHOT -e JBOSS_VERSION=19.0.0.Beta1-SNAPSHOT docker.io/wildflyeat 



# RUN EAT with OPENSHIFT (Additional instructions should be added relevant to the ones above)
---------------------------------------------------------------------------------------------

In order for run EAT Wildfly with Openshift, please, follow the next steps ...

1. Create the image for EAT Wildfly : e.g. docker build -t docker.io/eat .
2. Tag the previous image : e.g. docker tag docker.io/eat:latest docker.io/jboss/eat:wildfly1700B1
3. Push the tagged image to some repo : docker push docker.io/jboss/eat:wildfly1700B1
4. In Openshift create a project : e.g. eat
5. Get started with your project using 'Deploy Image'. e.g. choose image name and refer to the image uploaded at the repo in the previous step
6. Set the environment variables JBOSS_VERSION='the version of the server' and JBOSS_FOLDER=/wildfly/master/dist/target/wildfly-'the version of the server' 
7. Push the Deploy button and check EAT Wildfly running in Openshift (e.g. using the Logs)


In case the dependencies cannot be downloaded because of misconfiguration of openshift and for testing perposes the DockerfileLayer dirs can be used :

1. Use Dockerfile in DockerfileLayer1 dir to create image docker.io/eatwidlfly : docker build -t docker.io/eatwildfly .
2. Add in the container of this image the wildfly dist and the maven repo artifacts that are needed for EAT 
3. Commit the changes
4. Use Dockerfile in DockerfileLayer2 dir to create image docker.io/eat:wildfly1700B1 : docker build -t docker.io/eat:wildfly1700B1 .
5. Push the tagged image to some repo : docker push docker.io/jboss/eat:wildfly1700B1
6. In Openshift create a project : e.g. eat
7. Get started with your project using 'Deploy Image'. e.g. choose image name and refer to the image uploaded at the repo in the previous step
8. Set the environment variables JBOSS_VERSION='the version of the server' and JBOSS_FOLDER=/wildfly/master/dist/target/wildfly-'the version of the server' 
9. Push the Deploy button and check EAT Wildfly running in Openshift (e.g. using the Logs)

