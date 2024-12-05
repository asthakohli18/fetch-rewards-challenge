FROM gradle:8.10-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build bootJar --no-daemon

FROM amazoncorretto:17-alpine-jdk

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar /app/receipt-processor.jar

ENTRYPOINT ["java", "-jar", "/app/receipt-processor.jar"]