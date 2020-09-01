#!/bin/bash

set -e

if [ "$1" == "-v" ]; then
	./v.sh
	
elif [ "$1" == "-wildfly" ]; then
	export PROGRAM="https://github.com/wildfly/wildfly"
	export AT="https://github.com/EAT-JBCOMMUNITY/EAT"
	
	./v.sh
	echo ""
	
	echo "Building: Latest Wildfly + EAT"
	echo ""
	
	./pr.sh
elif [ "$1" == "-all" ]; then
	./all.sh $2 $3
elif [ "$1" == "-at" ]; then
	./at.sh
else 
	./pr.sh
fi
