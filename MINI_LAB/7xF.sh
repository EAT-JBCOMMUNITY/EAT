#!/bin/sh

cd ./Dockerfiles/JBossServers7x
podman build -t docker.io/eat7x -f DockerfileFedora --ulimit nofile=5000:5000 . > outputEap7x.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat7x
