#!/bin/sh

cd ./Dockerfiles/JBossServers71x
podman build -t docker.io/eat71x --ulimit nofile=5000:5000 . > outputEap71x.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat71x




