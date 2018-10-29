FROM mongo

COPY init.json /init.json
CMD mongoimport \
    -h mongo \
    --db financeapp \
    --collection financeUser \
    --type json \
    --file /init.json \
    --jsonArray
