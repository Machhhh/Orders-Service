FROM openjdk:8
ADD target/docker-orders-service.jar docker-orders-service.jar
VOLUME /tmp
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "docker-orders-service.jar"]