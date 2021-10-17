#!/bin/sh

cd ./Dockerfiles/JBossServers72x
podman build -t docker.io/eat72x --ulimit nofile=5000:5000 . > outputEap72x.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat72x
