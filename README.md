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

## Run from docker

Run app from pre compiled JAR with docker executing `docker-compose up` from docker folder

## Hosted on Heroku
https://minesweeper-emv.herokuapp.com/

## API Documentation with Swagger
https://minesweeper-emv.herokuapp.com/swagger-ui.html

## API Documentation with postman
https://documenter.getpostman.com/view/932175/Tz5jg1Mr

## How to play and what to expect

Creation game supports 3 parameters, board size (rows and columns) and mine percentage.
This game allow to play multiple games by user and pause/resume tracking time for each one. 
It supports flag and question mark.

