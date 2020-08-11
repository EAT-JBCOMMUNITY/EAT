#!/bin/bash

set -e

if [ "$1" == "-v" ]; then
	./v.sh
	
elif [ "$1" == "-wildfly" ]; then
	export SERVER="https://github.com/wildfly/wildfly"
	export EAT="https://github.com/EAT-JBCOMMUNITY/EAT"
	
	./v.sh
	echo ""
	
	echo "Building: Latest Wildfly + EAT"
	echo ""
	
	./pr.sh
elif [ "$1" == "-all" ]; then
	./all.sh
else 
	./pr.sh
fi
