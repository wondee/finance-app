FROM mongo

COPY init.json /init.json
CMD mongoimport \
    --uri mongodb://local.docker:27017/financeapp \
    --collection financeUser \
    --type json \
    --file /init.json \
    --jsonArray
    
