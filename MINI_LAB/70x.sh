#!/bin/sh

cd ./Dockerfiles/JBossServers70x
docker build -t docker.io/eat70x . > outputEap70x.txt

sudo docker stop $(sudo docker ps -a -q)
sudo docker rm $(sudo docker ps -a -q)
sudo docker image rm docker.io/eat70x -f




