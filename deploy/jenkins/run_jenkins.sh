#!/bin/bash
sudo docker run -d -p 8081:8080 -p 50000:50000  -v ~/jenkins/home:/var/jenkins_home:rw   -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker  --privileged --name jenkins wondee/jenkins

sudo docker exec -i -t 665b4a1e17b6 /bin/bash