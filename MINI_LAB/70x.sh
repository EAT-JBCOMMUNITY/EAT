#!/bin/sh

cd ./Dockerfiles/JBossServers70x
podman build -t docker.io/eat70x --ulimit nofile=5000:5000 . > outputEap70x.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat70x




