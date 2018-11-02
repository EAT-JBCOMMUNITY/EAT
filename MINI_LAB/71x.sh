#!/bin/sh

cd ./Dockerfiles/JBossServers71x
docker build -t docker.io/eat71x . > outputEap71x.txt

sudo docker image rm docker.io/eat71x -f




