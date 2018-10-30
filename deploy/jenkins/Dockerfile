FROM jenkins/jenkins:2.149

USER root

#RUN apt-get update && apt-get install -y docker.io && rm -rf /var/lib/apt/lists/*
#RUN apt-get install lxc

ENV DOCKERVERSION=18.03.1-ce
RUN curl -fsSLO https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKERVERSION}.tgz \
  && tar xzvf docker-${DOCKERVERSION}.tgz --strip 1 \
                 -C /usr/local/bin docker/docker \
  && rm docker-${DOCKERVERSION}.tgz

RUN usermod -aG docker jenkins

USER jenkins