FROM openjdk:8
ADD target/pretty-url.jar pretty-url.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "pretty-url.jar"]