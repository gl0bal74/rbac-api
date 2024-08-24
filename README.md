# rbac-api

## Installing

- Setup IntelliJ IDEA CE with Lombok plugin
- Install [Chocolatey](https://chocolatey.org/install)
- Install gradle
`brew install gradle`
Or on Windows
`choco install gradle`
- Install Docker Desktop from the web
- On Windows you'll need to install `make`
`choco install make`
- Install Flyway so you can clean up the database manually if you need to
`choco install flyway`

## Building and running the API in docker
`make docker`

## Testing the API Layer
Issue a postman request that uses `http://localhost:8080` with these urls
https://www.postman.com/winter-water-658702/workspace/dva-rbac-api/request/2152931-7dd8eff9-f790-4682-8394-4217a85ca927

## Checking the database
A Postgres server is running in docker and listening on 5432
database name `rbac`
username `user`
password `password`

## Manually cleaning up the database
`make clean-db`

## Running against a local Postgres Database
Startup Postgres in your local docker environment
```
docker pull postgres
docker run --name postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=rbac -e POSTGRES_USER=user -p 5432:5432 -d postgres
```
Then run the application with

`SQL_HOST=localhost make run`
