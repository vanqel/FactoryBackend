FROM  gradle:8.6-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN apt-get update
RUN apt-get install apt-utils -y    --no-install-recommends
RUN apt-get install libfreetype6 -y  --no-install-recommends
RUN apt-get install fontconfig -y --no-install-recommends
RUN apt-get install fonts-dejavu -y --no-install-recommends
RUN gradle build -x test

FROM openjdk:21-slim-buster
ARG VERSION=1.0.0
ARG PROJECT=factory
ENV JARNAME=$PROJECT-$VERSION.jar
COPY --from=build /home/gradle/src/build/libs/$JARNAME  /
ENTRYPOINT java -jar $JARNAME


