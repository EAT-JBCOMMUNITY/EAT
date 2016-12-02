if [ -z "${JAVA_HOME}" ]; then
  echo "JAVA_HOME if not set - this is required for the build to run."
  exit 1
else
  echo "JAVA_HOME:${JAVA_HOME}"
fi

if [ -z "${WORKSPACE}" ]; then
  echo "No WORKSPACE defined - is this script running inside Jenkins ? If not, set the WORKSPACE value."
  exit 2
fi

if [ -z "${MAVEN_HOME}" ]; then
  echo "No MAVEN_HOME defined, this is required to run the build."
  exit 3
fi

  readonly DOCKER_IMAGE=${DOCKER_IMAGE:-'rhel6-jenkins-shared-slave'}

  readonly DOCKER_JAVAZI_MOUNT=${DOCKER_JAVAZI_MOUNT:-'/usr/share/javazi-1.8/:/usr/share/javazi-1.8/'}
  readonly DOCKER_WORKSPACE_MOUNT="${WORKSPACE}:/workspace"
  readonly DOCKER_MAVEN_HOME_MOUNT="${MAVEN_HOME}:/maven_home"
  readonly DOCKER_JAVA_HOME_MOUNT="${JAVA_HOME}:/java"
  readonly DOCKER_OPT_MOUNT='/opt:/opt'

  readonly CONTAINER_ID=$(docker run -d -v "${DOCKER_WORKSPACE_MOUNT}" -v "${DOCKER_MAVEN_HOME_MOUNT}" -v "${DOCKER_JAVAZI_MOUNT}" -v ${LOCAL_REPO_DIR} -v "${DOCKER_JAVA_HOME_MOUNT}" -v "${DOCKER_OPT_MOUNT}" -v $(pwd):/job_home "${DOCKER_IMAGE}" -e LOCAL_REPO_DIR=${LOCAL_REPO_DIR} -e JBOSS_FOLDER=${JBOSS_FOLDER} -e JBOSS_FOLDER=${JBOSS_FOLDER} -e JBOSS_VERSION=${JBOSS_VERSION} -e JBOSS_VERSION_CODE=${JBOSS_VERSION_CODE})

  if [ "${?}" -ne 0 ]; then
    echo 'Failed to create container.'
    # just in case container is somehow running
    docker stop ${CONTAINER_ID}
    exit 4
  fi

  if [ -z "${CONTAINER_ID}" ]; then
    echo "No container - aborting"
    exit 5
  fi

  trap "docker stop ${CONTAINER_ID}" EXIT INT QUIT TERM
  docker exec "${CONTAINER_ID}" /bin/bash /job_home/jenkins-job.sh
  status=${?}
  docker stop "${CONTAINER_ID}"
  exit "${status}"
fi
