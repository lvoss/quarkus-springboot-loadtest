# quarkus-loadtest

This project aims to help with load-testing quarkus-native vs. standard quarkus in cloud environments. The project consists of one endpoint for calculating Prime numbers.

## Compiling to docker images

Create and run as native container:

```bash
./mvnw package -Dnative
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-loadtest .
docker run -i --rm -p 8080:8080 quarkus/quarkus-loadtest
```

Create and run as non-native container:

```bash
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-loadtest-jvm .
docker run -i --rm -p 8081:8080 quarkus/quarkus-loadtest-jvm
```

## Execute the load test

Once started, access the prime-calculation endpoint and choose a startNumber and count (how many prime numbers to calculate). 
If you don't set the query parameters, the default is used.

```bash
# native application
curl http://localhost:8080/prime-calculation?startNumber=10000000000000&count=250
# non-native application
curl http://localhost:8081/prime-calculation?startNumber=10000000000000&count=250
```

## Results from local testing

### Native quarkus application

* The build takes a lot longer (2 minutes w/o downloading dependencies), utilizing ~14 GB RAM and 8 CPU Cores, image size: 148 MB
* The application starts in 0.014 seconds

### Non-native quarkus application

* The build is faster (6.2 seconds w/o downloading dependencies), image size: 475 MB
* The application starts in 0.914 seconds

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.
