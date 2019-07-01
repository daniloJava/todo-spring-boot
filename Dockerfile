FROM debian:stretch
LABEL maintainer="Danilo Manoel Oliveira da Silva <danilo.manoel_oliveira@hotmail.com>"
ENV \
   SPRING_PROFILES_ACTIVE=native \
   JAVA_OPTS='-Xms64m -Xmx64m' \
   DEBUG_OPTS=

RUN \
   apt -y update \
   && apt-get -y install openjdk-8-jre-headless curl --no-install-recommends \
   && rm -rf /var/lib/apt/lists/*

VOLUME /tmp
EXPOSE ${PORT}

ARG JAR_FILE

COPY ${JAR_FILE} /app.jar
ENTRYPOINT exec java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar
