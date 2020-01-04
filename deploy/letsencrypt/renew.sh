#!/bin/bash

sudo certbot certonly --webroot -w /var/lib/docker/volumes/deploy_letsencrypt_www/_data -d finance.wondee.info -d jenkins.wondee.info
sudo su
cp -L /etc/letsencrypt/live/finance.wondee.info/* /var/lib/docker/volumes/deploy_letsencrypt_certs/_data/live/finance.wondee.info/

sudo docker-compose kill -s SIGHUP nginx