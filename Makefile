build:
	(cd front ; npm run build:local ; cd .. ; mvn clean package)

run:
	(java -jar back/target/back-0.0.1-SNAPSHOT.jar)

run_frontend_dev:
	(cd front ; npm run start)

run_backend_dev:
	(cd back ; mvn spring-boot:run -Dspring.profiles.active=local)
