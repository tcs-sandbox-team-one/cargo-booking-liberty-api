FROM adoptopenjdk/openjdk8-openj9 AS build-stage

RUN apt-get update && \
    apt-get install -y maven unzip

COPY . /project
WORKDIR /project

#RUN mvn -X initialize process-resources verify => to get dependencies from maven
RUN mvn clean package	
#RUN mvn --version
RUN mvn --version

RUN mkdir -p /config/apps && \
    mkdir -p /config/lib && \
    cp ./src/main/liberty/config/server.xml /config && \
    cp ./target/cargo-booking-service-1.0.war /config/apps/cargo-booking-service-1.0.war && \
    if [ ! -z "$(ls ./src/main/liberty/lib)" ]; then \
        cp ./src/main/liberty/lib/mysql-connector-java-8.0.17.jar /config/lib/mysql-connector-java-8.0.17.jar; \
    fi

#FROM ibmcom/websphere-liberty:webProfile7-ubi-min-amd64
FROM websphere-liberty:webProfile8

ARG SSL=true

ARG MP_MONITORING=true
ARG HTTP_ENDPOINT=false

RUN mkdir -p /opt/ibm/wlp/usr/shared/config/lib/global
COPY --chown=1001:0 --from=build-stage /config/ /config/

USER root
RUN chmod g+w /config/apps
USER 1001

# Upgrade to production license if URL to JAR provided
ARG LICENSE_JAR_URL
RUN \
   if [ $LICENSE_JAR_URL ]; then \
     wget $LICENSE_JAR_URL -O /tmp/license.jar \
     && java -jar /tmp/license.jar -acceptLicense /opt/ibm \
     && rm /tmp/license.jar; \
   fi
