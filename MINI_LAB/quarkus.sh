#!/bin/sh


cd ./Dockerfiles/quarkus
podman build -t docker.io/eat --ulimit nofile=5000:5000 . > outputQuarkus.txt

sudo podman stop $(sudo docker ps -a -q)
sudo podman rm $(sudo docker ps -a -q)
sudo podman image rm docker.io/eat -f


