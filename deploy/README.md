# Deployment

This folder contains the deployment scripts for the infrastructure.

Recreate container running in th docker-compose setup:
```
docker-compose up -d --no-deps --build <SERVICE NAME>
```