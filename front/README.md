# MineSweeper

Front build with Angular and back built with Java 11 with Postgres Database.

## Requirements
Postgres

Java 11

Node

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. 

The app will automatically reload if you change any of the source files.

Run back adding profile on VM parameters `-Dspring.profiles.active=local`

Postgres database and user should be added on application.properties

## Build

mvn clean package

## Swagger
http://localhost:8080/swagger-ui.html#/

## Hosted on Heroku
https://minesweeper-emv.herokuapp.com/
