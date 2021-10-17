#!/bin/sh

cd ./Dockerfiles/JBossServers64x
podman build -t docker.io/eat64x --ulimit nofile=5000:5000 . > outputEap64x.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat64x




