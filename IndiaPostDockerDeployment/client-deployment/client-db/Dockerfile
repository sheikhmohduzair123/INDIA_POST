FROM postgres:12.0-alpine
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
COPY init.sql /docker-entrypoint-initdb.d
