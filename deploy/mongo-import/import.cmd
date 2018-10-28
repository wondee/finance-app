docker build -t  wondee/finance-mongo-import .
docker run --network=deploy_mongo_import wondee/finance-mongo-import
