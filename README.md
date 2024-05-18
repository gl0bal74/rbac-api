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
A MySQL server is running in docker and listening on 3306
database name `rbac`
username `user`
password `password`

## Manually cleaning up the database
`make clean-db`