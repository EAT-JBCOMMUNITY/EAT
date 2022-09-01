#!/bin/sh

cd ./Dockerfiles/wildflyjakarta
podman build -t docker.io/eat --ulimit nofile=5000:5000 . > outputWildflyJakarta.txt

#sudo podman stop $(sudo docker ps -a -q)
#sudo podman rm $(sudo docker ps -a -q)
sudo podman rmi -f docker.io/eat


