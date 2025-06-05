FROM amazoncorretto:17-al2023-jdk

WORKDIR /app

COPY ./target/container1-0.0.1-SNAPSHOT.jar container1.jar

EXPOSE 6000

CMD ["java", "-jar", "container1.jar"]
