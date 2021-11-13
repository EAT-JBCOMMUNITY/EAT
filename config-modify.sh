#!/bin/bash

if [ -z "${SERVER_DIR_NAME}" ]; then
  echo "No SERVER_DIR_NAME has been defined."
  exit 1
fi

if [ -z "${SERVER_POM_CONFIG}" ]; then
  echo "No SERVER_POM_CONFIG has been defined."
  exit 2
fi

cp ${SERVER_POM_CONFIG} ./modules/testcases/jdkAll/${SERVER_DIR_NAME}

