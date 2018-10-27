FROM mongo

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait

COPY init.json /init.json
CMD /wait && mongoimport \
    -h mongo \
    --db financeapp \
    --collection financeUser \
    --type json \
    --file /init.json \
    --jsonArray