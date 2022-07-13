docker run -d --name dev-postgres -e POSTGRES_PASSWORD=admin -v ./postgres-data/:/var/lib/postgresql/data -p 5432:5432 postgres

docker run -p 82:80 -e 'PGADMIN_DEFAULT_EMAIL=user@domain.local' -e 'PGADMIN_DEFAULT_PASSWORD=admin' --name dev-pgadmin -d dpage/pgadmin4


# docker inspect dev-postgres (and check IP adress)
# user is postgres 