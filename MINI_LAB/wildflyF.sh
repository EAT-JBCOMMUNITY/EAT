#!/bin/sh

cd ./Dockerfiles/wildfly
docker build -t docker.io/eat -f DockerfileFedora --ulimit nofile=5000:5000 . > outputWildfly.txt

#sudo docker stop $(sudo docker ps -a -q)
#sudo docker rm $(sudo docker ps -a -q)
sudo docker rmi -f docker.io/eat


