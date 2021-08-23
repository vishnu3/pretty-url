# URL Lookup
Stylight pretty url application shows how to
deploy [SpringBoot](http://projects.spring.io/spring-boot/) RESTful web service application
with [Docker](https://www.docker.com/)

#### Prerequisite

Installed:   
[Docker](https://www.docker.com/)   
[git](https://www.digitalocean.com/community/tutorials/how-to-contribute-to-open-source-getting-started-with-git)   
[Java 1.8](https://www.oracle.com/technetwork/java/javase/overview/index.html)  
[Maven 3.6.3](https://maven.apache.org/install.html)

Optional:   
[Docker-Compose](https://docs.docker.com/compose/install/)

## Steps

### Clone source code from git

```
$  https://github.com/vishnu3/pretty-url.git
```

### Running the Application

#### Option 1:

##### Building the jar

```
$ mvn clean package
```

##### Build Docker image read CreateDockerImage file to create docker image

```
$ docker build -t pretty-url-app .
```

Maven build had been executed before creation of the docker image.

> Note:if you run this command for first time it will take some time in order to download base image from [DockerHub](https://hub.docker.com/)

##### Run Docker Container

```
docker run --name pretty-url -d -p 8080:8080 -e JAVA_OPTS="-Xms256m -Xmx512m" -it --rm pretty-url-app
```

#### Option 2:

All the above instructions are added in docker sh, after installing necessary pre-requisities

##### make docker sh file executable in terminal or command line

```
chmod u+x docker.sh
```

#### Running the shell script

```
./docker.sh
```

#### Option 3:

If you have installed your ide, then you can import the project and run as SpringBootApplication.

### Test application

1. [Postman](https://www.postman.com/)
2. curl command

````
curl -X POST "http://localhost:8080/pretty-url/canonical" -H "accept: */*" -H "Content-Type: application/json" -d "[ \"/Fashion/\", \"/Women/\"]"
````

##### The response would be 200 OK

````
{
  "https://www.stylight.com/products?gender=female": "https://www.stylight.com/Women/"
}
````

{
"https://www.stylight.com/Women/": "https://www.stylight.com/products?gender=female",
"https://www.stylight.com/Fashion/": "https://www.stylight.com/products"
}

````
curl -X POST "http://localhost:8080/pretty-url/pretty" -H "accept: */*" -H "Content-Type: application/json" -d "[ \"/products?gender=female\"]"
````

##### The response would be 200 OK

````
{
  "https://www.stylight.com/products?gender=female": "https://www.stylight.com/Women/"
}
````

##### Stop Docker Container:

```
docker stop `docker container ls | grep "pretty-url-app:*" | awk '{ print $1 }'`
```

## Run with docker-compose for scalability

Build and start the container by running

```
$ docker-compose up -d 
```

##### Test application with

1. [Swagger](https://localhost:8080/pretty-url/swagger-ui/index.html)
2. [Postman](https://www.postman.com/)
3. curl command - [Refer above]

##### Stop Docker Container:

```
docker-compose down
```