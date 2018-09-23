FROM openjdk:8-jdk-alpine
COPY ./target/finance-application.jar /usr/src/finapp/
WORKDIR /usr/src/finapp
EXPOSE 8080
CMD ["java", "-jar", "finance-application.jar"]