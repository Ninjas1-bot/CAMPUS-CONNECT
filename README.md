# Campus Connect - Student Portal (Demo)

This is a simple Spring Boot + Thymeleaf demo project for the **Campus Connect** student portal.
It uses in-memory sample data (no database) so you can run and explore quickly.

## Quickstart

Requirements:
- Java 17+
- Maven (if you don't have mvnw scripts)

Run with Maven:
```bash
mvn spring-boot:run
```

Open: http://localhost:8083

Login with sample account:
- **Email:** test@example.com
- **Password:** 123

## Project structure
See `src/main/java` and `src/main/resources/templates` for controllers and templates.

You can later add persistence (H2 / SQLite) or registration page as next steps.

Added demo features:
- Simple Java quiz: open /quiz, answer a sample question and get a score.
- Simple Events & Booking: open /bookings, view sample events and book them (bookings are stored in the HTTP session).

Run:
```bash
mvn spring-boot:run
```

Open: http://localhost:8080

Sample login (demo):
- Email: test@example.com
- Password: 123
