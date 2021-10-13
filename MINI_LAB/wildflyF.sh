#!/bin/sh

cd ./Dockerfiles/wildfly
podman build -t docker.io/eat -f DockerfileFedora --ulimit nofile=5000:5000 . > outputWildflyF.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat


