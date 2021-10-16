#!/bin/sh

cd ./Dockerfiles/JBossServers73x
podman build -t docker.io/eat73x --ulimit nofile=5000:5000 . > outputEap73x.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat73x
