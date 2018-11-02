#!/bin/sh


cd ./Dockerfiles/wildfly
docker build -t docker.io/eat . > outputWildfly.txt

sudo docker image rm docker.io/eat -f


