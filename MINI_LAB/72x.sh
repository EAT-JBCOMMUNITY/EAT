#!/bin/sh

cd ./Dockerfiles/JBossServers72x
docker build -t docker.io/eat72x . > outputEap72x.txt

sudo docker image rm docker.io/eat72x -f
