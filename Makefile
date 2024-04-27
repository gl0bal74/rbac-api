build:
	gradle bootJar

docker: build
	docker compose up -d