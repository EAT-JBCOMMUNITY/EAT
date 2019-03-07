#!/bin/sh


cd ./Dockerfiles/quartus
docker build -t docker.io/eat . > outputQuartus.txt

sudo docker stop $(sudo docker ps -a -q)
sudo docker rm $(sudo docker ps -a -q)
sudo docker image rm docker.io/eat -f


