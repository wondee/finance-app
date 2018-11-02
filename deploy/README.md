# Deployment

This folder contains the deployment scripts for the infrastructure.

Recreate container running in th docker-compose setup:
```
docker-compose up -d --no-deps --build <SERVICE NAME>
```

Import data to the running mongo db:

- add FinanceUser data to `/mongo-import/init.json`
- run import script. The prefix for the network ('deploy') is named as the same directory the `docker-compose up` was run in.


Creating a SSL certificate for local:

docker run -i -t -v deploy_letsencrypt_certs:/etc/letsencrypt debian:jessie-backports mkdir -p /etc/letsencrypt/live/finance.wondee.info mkdir -p /etc/letsencrypt/live/finance.wondee.info && openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/letsencrypt/live/finance.wondee.info/privkey.pem  -out /etc/letsencrypt/live/finance.wondee.info/fullchain.pem  -subj /CN=finance.wondee.info

Might not work... 