# MineSweeper

Frontend built with Angular and backend built with Java 11 and Postgres DB.

## Requirements
### Postgres
Create database and user with script provided in folder scripts or change configuration from application.properties

`psql -f scripts/create-database.sql`

### Java 11

### Node 12


## Development server

### Front
Run `make run_frontend_dev` for a front dev server. Navigate to `http://localhost:4200/`. 

The app will automatically reload if you change any of the source files.

### Back

`make run_backend_dev` Or run from IDE adding profile local on VM parameters `-Dspring.profiles.active=local`


## Build

`make build`

## Build and run local fron Jar

`make build`

`make run`

## Swagger
http://localhost:8080/swagger-ui.html#/

## Hosted on Heroku
https://minesweeper-emv.herokuapp.com/

## Docker

Run app from pre compiled JAR with docker executing `docker-compose up` from docker folder
