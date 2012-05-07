#!/bin/bash

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4000  -jar commons-user-service/target/commons-user-service-1.0-SNAPSHOT.jar server commons-user-service/src/main/resources/user-service.yml
