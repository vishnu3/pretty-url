#!/bin/sh

mvn clean package
docker build -t pretty-url-app .
docker run --name pretty-url -d -p 8080:8080 -e JAVA_OPTS="-Xms256m -Xmx512m" -it --rm pretty-url-app