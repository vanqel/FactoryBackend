FROM  gradle:8.6-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

RUN apt-get install
FROM openjdk:21
ARG VERSION=1.0.0
ARG PROJECT=factory
ENV JARNAME=$PROJECT-$VERSION.jar
COPY --from=build /home/gradle/src/build/libs/$JARNAME  /

EXPOSE 8099

ENTRYPOINT java -jar $JARNAME


