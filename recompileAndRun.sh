#!/bin/bash

mvn clean install
java -jar hbo-image-service/target/hbo-image-service-1.0-SNAPSHOT.jar server hbo-image-service/src/main/resources/hello-world.yml
