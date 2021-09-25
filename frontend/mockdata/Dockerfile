FROM node:11

WORKDIR /app

RUN npm install -g mockserver

COPY ./ /app/

EXPOSE 8080

ENTRYPOINT ["mockserver", "-p 8080", "-m /app/"]
