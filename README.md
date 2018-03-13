# Sample Employee REST Server

A sample web application that exposes REST operations for employees.

## Prerequisites

- JDK 8+.
- A MySQL database.

### Database

- Connects to a MySQL database at `localhost:3306/sampledb` with username `user_sampledb` and password `pw_sampledb` by default.
- These values can be changed in `src/main/resources/application.properties`.
- A MySQL server can be started via Docker with the following command:

```
docker run -d \
-e MYSQL_DATABASE=sampledb \
-e MYSQL_USER=user_sampledb \
-e MYSQL_PASSWORD=pw_sampledb \
-p 3306:3306 \
--name=mysql_sample \
mysql/mysql-server:latest
```

The container can be stopped and removed via `docker rm -f mysql_sample`.

## Build

`./mvnw package`

## Run

`java -jar target/sample-employee-rest-server-0.0.1-SNAPSHOT.jar`