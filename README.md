# Java from Zero

Code for the **Java from Zero** video series: one chapter at a time, using Java 25 LTS. The running project is Shelf, a lending-library app that starts as a single file and ends as a multi-module Maven build with tests, concurrency, JSON and HTTP, and a Spring Boot REST API on PostgreSQL.

The repository root holds the finished project from the last chapter. Every chapter also has its own folder under `chapters/` with the project exactly as it stands at the end of that chapter, so you can start from any of them. Each folder runs on its own: `cd` into it and use the commands from the video.

## Requirements

- A JDK 25, for example Eclipse Temurin 25 from [adoptium.net](https://adoptium.net/).
- From chapter 11 on, Maven runs through the wrapper (`./mvnw`, or `mvnw.cmd` on Windows), which downloads Maven 3.10.0 on first use. Gradle 9.9.1 is optional, through `./gradlew`.
- PostgreSQL 18 for chapter 15. The API expects a database `shelf` owned by user `shelf` (password `shelf`) on `localhost:5442`; change `shelf-api/src/main/resources/application.properties`, or set `SPRING_DATASOURCE_URL`, to use another server.

## Run the finished project

```
./mvnw -B verify                      # shelf-core and shelf-cli, with tests and a JaCoCo report
java -cp shelf-core/target/shelf-core-1.0.jar:shelf-cli/target/shelf-cli-1.0.jar shelf.Main

cd shelf-api
./mvnw -B test                        # needs the PostgreSQL database above
./mvnw -B package -DskipTests
java -jar target/shelf-api-0.0.1-SNAPSHOT.jar
curl -s localhost:8096/books
```

On Windows, use `;` instead of `:` in class paths. `shelf-api/Dockerfile` and `shelf-api/compose.yaml` are a starting point for containers; they need Docker and were not run in the videos.

## Chapters

| # | Chapter | Folder |
|---|---------|--------|
| 01 | Install Java and Run Your First Program | [chapters/01-install-java-and-run-your-first-program](chapters/01-install-java-and-run-your-first-program) |
| 02 | Types, Operators and Control Flow | [chapters/02-types-operators-and-control-flow](chapters/02-types-operators-and-control-flow) |
| 03 | Classes and Objects | [chapters/03-classes-and-objects](chapters/03-classes-and-objects) |
| 04 | Inheritance, Interfaces and Polymorphism | [chapters/04-inheritance-interfaces-and-polymorphism](chapters/04-inheritance-interfaces-and-polymorphism) |
| 05 | Strings, Text Blocks and Dates | [chapters/05-strings-text-blocks-and-dates](chapters/05-strings-text-blocks-and-dates) |
| 06 | Collections: Lists, Sets and Maps | [chapters/06-collections-lists-sets-and-maps](chapters/06-collections-lists-sets-and-maps) |
| 07 | Generics | [chapters/07-generics](chapters/07-generics) |
| 08 | Exceptions and Resources | [chapters/08-exceptions-and-resources](chapters/08-exceptions-and-resources) |
| 09 | Lambdas and Streams | [chapters/09-lambdas-and-streams](chapters/09-lambdas-and-streams) |
| 10 | Pattern Matching and Modern Java | [chapters/10-pattern-matching-and-modern-java](chapters/10-pattern-matching-and-modern-java) |
| 11 | Build with Maven and Gradle | [chapters/11-build-with-maven-and-gradle](chapters/11-build-with-maven-and-gradle) |
| 12 | Test with JUnit and Mockito | [chapters/12-test-with-junit-and-mockito](chapters/12-test-with-junit-and-mockito) |
| 13 | Concurrency | [chapters/13-concurrency](chapters/13-concurrency) |
| 14 | Files, HTTP and JSON | [chapters/14-files-http-and-json](chapters/14-files-http-and-json) |
| 15 | Build a REST API with Spring Boot and PostgreSQL | [chapters/15-build-a-rest-api-with-spring-boot-and-postgresql](chapters/15-build-a-rest-api-with-spring-boot-and-postgresql) |

## License

MIT
