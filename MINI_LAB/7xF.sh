#!/bin/sh

cd ./Dockerfiles/JBossServers7x
docker build -t docker.io/eat7x -f DockerfileFedora --ulimit nofile=5000:5000 . > outputEap7x.txt

#sudo docker stop $(sudo docker ps -a -q)
#sudo docker rm $(sudo docker ps -a -q)
sudo docker rmi -f docker.io/eat7x
