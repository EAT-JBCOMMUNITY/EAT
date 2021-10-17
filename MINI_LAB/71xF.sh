#!/bin/sh

cd ./Dockerfiles/JBossServers71x
podman build -t docker.io/eat71x  -f DockerfileFedora --ulimit nofile=5000:5000 . > outputEap71xF.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat71x




