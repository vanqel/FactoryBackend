FROM  gradle:8.6-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test
FROM openjdk:21-slim-buster
ARG VERSION=1.0.0
ARG PROJECT=openstore
ENV JARNAME=$PROJECT-$VERSION.jar
COPY --from=build /home/gradle/src/build/libs/$JARNAME  /
EXPOSE 8099
ENTRYPOINT java -jar $JARNAME


