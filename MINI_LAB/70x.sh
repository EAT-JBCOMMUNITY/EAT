#!/bin/sh

cd ./Dockerfiles/JBossServers70x
docker build -t docker.io/eat70x --ulimit nofile=5000:5000 . > outputEap70x.txt

#sudo docker stop $(sudo docker ps -a -q)
#sudo docker rm $(sudo docker ps -a -q)
sudo docker rmi -f docker.io/eat70x




