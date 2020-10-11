#!/bin/sh

cd ./Dockerfiles/JBossServers71x
docker build -t docker.io/eat71x --ulimit nofile=5000:5000 . > outputEap71x.txt

#sudo docker stop $(sudo docker ps -a -q)
#sudo docker rm $(sudo docker ps -a -q)
sudo docker rmi -f docker.io/eat71x




