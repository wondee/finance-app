

Run mongo server locally:

docker run -d -p 27017:27017 -v ~/data:/data/db mongo

SSH to Jenkins instance:

ssh -L 127.0.0.1:8085:localhost:8080 [username]@wondee-jenkins.westeurope.cloudapp.azure.com
