FROM openjdk:17-alpine3.13

ARG JAR_FILE=build/libs/account.management-0.0.1-SNAPSHOT.jar

RUN mkdir /opt/account-management
WORKDIR /opt

COPY ${JAR_FILE} /opt/account-management/account-management-api.jar

EXPOSE 8080
#Commented this code for k8s
#CMD ["java", "-jar", "account-management/account-management-api.jar"]