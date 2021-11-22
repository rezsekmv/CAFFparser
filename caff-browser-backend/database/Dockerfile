FROM mysql:8
WORKDIR /db

EXPOSE 3306

COPY init.sql /docker-entrypoint-initdb.d/init.sql
