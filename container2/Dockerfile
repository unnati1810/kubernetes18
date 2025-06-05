FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY ./target/container2-0.0.1-SNAPSHOT.jar container2.jar

EXPOSE 6002

ENTRYPOINT ["java","-jar","container2.jar"]

# RUN chmod 666 container2.jar