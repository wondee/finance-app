

Run mongo server locally:

docker run -d -p 27017:27017 -v ~/data:/data/db mongo

Running

java -jar target/finance-application.jar --spring.data.mongodb.uri="[mongo db connection string]"

Connection String from local: mongodb://local.docker/financeapp

Docker image name: wondee/finance-application

docker run -p 80:8080 -e "SPRING_DATA_MONGODB_URI=mongodb://[local mongo db host]/financeapp" wondee/finance-application

Production Logfiles under:

ftp://waws-prod-dm1-023.ftp.azurewebsites.windows.net/LogFiles/

Username/Password found under "publish profile"
