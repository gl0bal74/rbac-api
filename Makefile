clean:
	gradle clean

build: clean
	gradle bootJar

copy-jar: build
	cp build/libs/rbac-api-0.0.1-SNAPSHOT.jar .

docker: copy-jar
	docker compose up -d

clean-db:
	flyway -url=jdbc:postgres://localhost:5432/rbac -user=user -password=password  -cleanDisabled=false clean

run: build
	gradle bootRun