#!/bin/sh

cd ./Dockerfiles/wildfly
docker build -t docker.io/eatF -f DockerfileFedora --ulimit nofile=5000:5000 . > outputWildflyF.txt

#sudo docker stop $(sudo docker ps -a -q)
#sudo docker rm $(sudo docker ps -a -q)
sudo docker rmi -f docker.io/eatF


