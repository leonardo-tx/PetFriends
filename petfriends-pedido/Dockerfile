FROM maven:4.0.0-rc-4-eclipse-temurin-21 AS build

WORKDIR /build

COPY petfriends-almoxarifado/src petfriends-almoxarifado/src
COPY petfriends-almoxarifado/pom.xml petfriends-almoxarifado/pom.xml
COPY petfriends-transporte/src petfriends-transporte/src
COPY petfriends-transporte/pom.xml petfriends-transporte/pom.xml
COPY petfriends-pedido/src petfriends-pedido/src
COPY petfriends-pedido/pom.xml petfriends-pedido/pom.xml
COPY pom.xml pom.xml

RUN mvn clean install -DskipTests
RUN mvn -pl petfriends-pedido clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /build/petfriends-pedido/target/*.jar petfriends-pedido.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "petfriends-pedido.jar"]