# finance-app

[![Build Status](https://travis-ci.org/wondee/finance-app.svg?branch=master)](https://travis-ci.org/wondee/finance-app)

A simple financial planning application. Manage your fixed costs and see a forecast of you finances in a nice overview.


Production Link: https://finance.wondee.info  (Demo Credentials: Demo/Demo)

## Cheat sheet:

Run mongo server locally:

```
docker run -d -p 27017:27017 -v ~/data:/data/db mongo
```

Running
```
java -jar target/finance-application.jar --spring.data.mongodb.uri="[mongo db connection string]"
```
or as JVM parameter
```
-DSPRING_DATA_MONGODB_URI=mongodb://local.docker/financeapp
```

Connection String from local: `mongodb://local.docker/financeapp`

Docker image name: wondee/finance-application

```
docker run -p 80:8080 -e "SPRING_DATA_MONGODB_URI=mongodb://[local mongo db host]/financeapp" wondee/finance-application
```

Certificate is created by letsencrypt.

For Windows to able to moiunt local drives: `set COMPOSE_CONVERT_WINDOWS_PATHS=1`
