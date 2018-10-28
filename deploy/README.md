# Deployment

This folder contains the deployment scripts for the infrastructure.

Recreate container running in th docker-compose setup:
```
docker-compose up -d --no-deps --build <SERVICE NAME>
```

Import data to the running mongo db:

. add FinanceUser data to `/mongo-import/init.json`
. run import script. The prefix for the network ('deploy') is named as the same directory the `docker-compose up` was run in. 