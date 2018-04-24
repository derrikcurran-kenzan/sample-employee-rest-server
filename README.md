# Sample Employee REST Server

A sample web application that exposes REST operations for employees.

## Prerequisites

- Java 8+.
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
mysql/mysql-server:5.7
```

The Docker container can be stopped and removed via `docker rm -f mysql_sample`.

## Run

`./mvnw spring-boot:run`

## Tests

`./mvnw test`

## Build JAR

`./mvnw package -DskipTests`

## Deploy to Local Kubernetes Node

This assumes you're using Minikube and includes a MySQL server deployment.

```
eval $(minikube docker-env) && \
./mvnw package -DskipTests && \
docker build --tag employees-backend:current . && \
kubectl create --recursive -f kubernetes/
```

### Access via Kubernetes Proxy:

Run `kubectl proxy --port=8080`, and service will be accessible at <http://localhost:8080/api/v1/namespaces/default/services/employees-backend:http/proxy/employees>.


## Usage

### Authentication

Requests to secure endpoints require an authorization header with a valid access token, like this:

`Authorization: Bearer {token}`

To get a token, send a POST request to `/auth/sign-in` with the following JSON body:

```json
{
  "Username": "admin",
  "Password": "admin"
}
```

The above `admin:admin` user is created automatically on DB initialization and can be used to request a token.

### Employees

#### Spec

```json
{
  "ID": 1,
  "FirstName": "Jane",
  "MiddleInitial": "J",
  "LastName": "Doe",
  "DateOfBirth": "1988-06-20",
  "DateOfEmployment": "2016-02-15",
  "Status": "ACTIVE"
}
```

- FirstName is required.
- Status can be either ACTIVE or INACTIVE.
- Only active employees are exposed by the API.

#### Endpoints

| Method | URI             | Description                              | Access              |
|--------|-----------------|------------------------------------------|---------------------|
| GET    | /employees      | Get all active employees.                | Public              |
| GET    | /employees/{id} | Get an active employee by ID.            | Public              |
| POST   | /employees      | Create an employee.                      | Public              |
| PUT    | /employess/{id} | Update an active employee by ID.         | Public              |
| DELETE | /employees/{id} | Change an employee's status to INACTIVE. | Authenticated Users |

### Admin

#### Endpoints

| Method | URI             | Description                              | Access              |
|--------|-----------------|------------------------------------------|---------------------|
| GET    | /admin/network  | Get local IP and hostname.               | Public              |