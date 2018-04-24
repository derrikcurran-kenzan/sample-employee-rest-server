FROM openjdk:8-jdk

COPY target/sample-employee-rest-server-*.jar /app/service.jar

ENTRYPOINT ["java", "-jar", "/app/service.jar"]

EXPOSE 8081
