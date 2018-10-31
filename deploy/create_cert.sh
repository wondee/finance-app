#!/bin/bash

sudo certbot certonly --webroot -w /var/lib/docker/volumes/deploy_letsencrypt_www/_data -d finance-vm.wondee.info -d jenkins.wondee.info -d finance.wondee.info

sudo docker-compose kill -s SIGHUP nginx

# copy symlink

p -Lr /usr/share/solr/ ~/solrTest