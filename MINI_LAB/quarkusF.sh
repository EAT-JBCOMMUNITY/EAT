#!/bin/sh


cd ./Dockerfiles/quarkus
podman build -t docker.io/eat -f DockerfileFedora --ulimit nofile=5000:5000 . > outputQuarkusF.txt

sudo podman stop $(sudo docker ps -a -q)
sudo podman rm $(sudo docker ps -a -q)
sudo podman image rm docker.io/eat -f


