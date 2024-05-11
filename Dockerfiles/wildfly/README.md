# RUN EAT WITH DOCKER IMAGES
-----------------------------

In order for run EAT using docker to test Wildfly Server Images, please, follow the next steps ...

1. Create the image for the Wildfly Server : e.g. docker build -t docker.io/wildflyserver -f DockerfileWildflyImage --ulimit nofile=5000:5000 .
2. Run the container attaching two volumes : e.g. docker run --name=wildflyserver -v DataVolume1:/wildfly/master -v DataVolume2:/home/user/.m2 docker.io/wildflyserver
3. Create the image for EAT : e.g. docker build -t docker.io/wildflyeat -f DockerfileEat --ulimit nofile=5000:5000 .
4. Run the container with the same two volumes attached, specifying the server version and the path to the server distribution : e.g. docker run --name=wildflyeat -v DataVolume1:/wildfly/master -v DataVolume2:/home/user/.m2 -e JBOSS_FOLDER=/wildfly/master/dist/target/wildfly-19.0.0.Beta1-SNAPSHOT -e JBOSS_VERSION=19.0.0.Beta1-SNAPSHOT --ulimit nofile=5000:5000 docker.io/wildflyeat 



# RUN EAT with OPENSHIFT (Additional instructions should be added relevant to the ones above)
---------------------------------------------------------------------------------------------

In order for run EAT Wildfly with Openshift, please, follow the next steps ...

1. Create the image for EAT Wildfly : e.g. docker build -t docker.io/eat --ulimit nofile=5000:5000 .
2. Tag the previous image : e.g. docker tag docker.io/eat:latest docker.io/jboss/eat:wildfly1700B1
3. Push the tagged image to some repo : docker push docker.io/jboss/eat:wildfly1700B1
4. In Openshift create a project : e.g. eat
5. Get started with your project using 'Deploy Image'. e.g. choose image name and refer to the image uploaded at the repo in the previous step
6. Set the environment variables JBOSS_VERSION='the version of the server', JBOSS_FOLDER=/wildfly/master/dist/target/wildfly-'the version of the server' and EAT_MODULES=(basic, jaxrs, ejb, domain, etc) if a specific eat module should be tested (if not set, all modules are tested)
7. Push the Deploy button and check EAT Wildfly running in Openshift (e.g. using the Logs)


Run EAT Wildfly with Openshift, using config files :

1. Create a build config that builds an image from the current dir's Dockerfile and uploads it at some registry (e.g. https://index.docker.io/v1/). Probably the creation of a secret in order to push to the registry will be needed. (an example of such a build config is included at the current dir)
2. Create a deployment/pod or job that runs the image. The env variables should be passed as args of the command in the deployment/job config (an example of deployment-config and job-config is included at the current dir).


In case the dependencies cannot be downloaded because of misconfiguration of openshift and for testing perposes the DockerfileLayer dirs can be used :

1. Use Dockerfile in DockerfileLayer1 dir to create image docker.io/eatwidlfly : docker build -t docker.io/eatwildfly .
2. Add in the container of this image the wildfly dist and the maven repo artifacts that are needed for EAT 
3. Commit the changes
4. Use Dockerfile in DockerfileLayer2 dir to create image docker.io/eat:wildfly1700B1 : docker build -t docker.io/eat:wildfly1700B1 --ulimit nofile=5000:5000 .
5. Push the tagged image to some repo : docker push docker.io/jboss/eat:wildfly1700B1
6. In Openshift create a project : e.g. eat
7. Get started with your project using 'Deploy Image'. e.g. choose image name and refer to the image uploaded at the repo in the previous step
8. Set the environment variables JBOSS_VERSION='the version of the server' and JBOSS_FOLDER=/wildfly/master/dist/target/wildfly-'the version of the server' 
9. Push the Deploy button and check EAT Wildfly running in Openshift (e.g. using the Logs)

