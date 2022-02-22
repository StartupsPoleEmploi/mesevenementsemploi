# Build Stage
FROM maven:3.6.3-jdk-11-slim AS builder
WORKDIR /build
COPY .mvn .mvn
COPY pom.xml .
# Copy the project source
COPY src src

# Package the application
RUN mvn clean package
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Run Stage
FROM openjdk:11-slim
LABEL MAINTAINER="Les Monstreuils"
LABEL Project="mee-domaine"

ARG DEPENDENCY=/build/target/dependency

# Mise à l'heure française
ARG TIMEZONE=${TIMEZONE:-Europe/Paris}
ENV TZ $TIMEZONE
RUN export DEBIAN_FRONTEND=noninteractive && \
    apt-get update && \
    apt-get -y install tzdata
RUN echo "$TIMEZONE" >/etc/timezone; \
    ln -sf /usr/share/zoneinfo/$TIMEZONE /etc/localtime; \
    dpkg-reconfigure -f noninteractive tzdata;


# Copy the dependency application file from build stage
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","fr.pe.domaine.peactions.PeActionsDomaineApplication"]

