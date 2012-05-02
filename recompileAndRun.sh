#!/bin/bash

mvn clean install
#java -jar commons-user-service/target/commons-user-service-1.0-SNAPSHOT.jar server commons-user-service/src/main/resources/hello-world.yml
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=4000  -jar commons-user-service/target/commons-user-service-1.0-SNAPSHOT.jar server commons-user-service/src/main/resources/user-service.yml
