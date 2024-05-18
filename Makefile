clean:
	gradle clean

build: clean
	gradle bootJar

copy-jar: build
	cp ./build/libs/rbac-api-0.0.1-SNAPSHOT.jar .

docker: copy-jar
	docker compose up -d

clean-db:
	flyway -url=jdbc:mysql://localhost:3306/rbac -user=user -password=password  -cleanDisabled=false clean