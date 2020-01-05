FROM node:11

WORKDIR /app

RUN npm install -g mockserver

COPY ./ /app/

RUN ls -l /app/

EXPOSE 8081

ENTRYPOINT ["mockserver", "-m /app/"]
