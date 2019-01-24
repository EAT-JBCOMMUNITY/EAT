#!/bin/sh

cd ./Dockerfiles/JBossServers64x
docker build -t docker.io/eat64x . > outputEap64x.txt

#sudo docker stop $(sudo docker ps -a -q)
#sudo docker rm $(sudo docker ps -a -q)
sudo docker rmi -f docker.io/eat64x




