docker-compose run --rm letsencrypt \
  letsencrypt certonly --webroot \
  --email markus.wondrak@gmail.org --agree-tos \
  -w /var/www/letsencrypt -d finance-vm.wondee.info


docker-compose kill -s SIGHUP nginx