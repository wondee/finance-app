certbot certonly --webroot -w /var/lib/docker/volumes/deploy_letsencrypt_www/_data -d finance-vm.wondee.info -d jenkins.wondee.info -d finance.wondee.info

docker-compose kill -s SIGHUP nginx