# Java-Game-of-Life
An implementation of Joseph Conway's Game of Life (my favorite flavor of Hello World) to brush up on my modern Java chops.

Created with Java 21 and JavaFX. Uses Maven for build management, JUnit 5 for tests, Spotless for linting, and GitHub Actions for continuous integration.

# Build and Run

 1. Clone the repo.
 2. Install Maven if it isn't already in your environment.
 3. `mvn java:fx`

## Other commands

 - `mvn test` to run JUnit tests.
 - `mvn spotless:check` to run the linter.
 - `mvn spotless:apply` to auto-fix linting issues.
   - MRs fail the CI pipeline if Spotless isn't satisfied.