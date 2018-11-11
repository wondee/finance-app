FROM adoptopenjdk/openjdk11:alpine-slim
COPY ./target/finance-application.jar /usr/src/finapp/
WORKDIR /usr/src/finapp
EXPOSE 8080

CMD ["java", "-jar", "finance-application.jar"]