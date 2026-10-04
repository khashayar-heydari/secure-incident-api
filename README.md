# Secure Incident Tracker API

A small Java and Spring Boot project for learning how to build a REST API for security incidents.

The current version provides a health endpoint and returns two fixed example incidents. Incidents are not stored in a database yet.

## Requirements

- Java 21 (JDK)

The project includes a Maven Wrapper, so a separate Maven installation is not required.

The commands below are written for Windows PowerShell.

## Run locally

Open a terminal in the project folder and run:

```powershell
.\mvnw.cmd spring-boot:run
```

The application starts at `http://localhost:8080`. Press `Ctrl+C` in the terminal to stop it.

## API endpoints

| Method | Path | Current response |
| --- | --- | --- |
| GET | `/api/health` | The text `OK` |
| GET | `/api/incidents` | A list containing two fixed example incidents |
| GET | `/api/incidents/{id}` | The incident with that ID, or 404 if it does not exist |

Example response from `GET /api/incidents`:

```json
[
  {
    "id": 1,
    "title": "Suspicious login",
    "description": "Several failed login attempts were detected.",
    "status": "OPEN"
  },
  {
    "id": 2,
    "title": "Phishing email",
    "description": "An employee reported an email containing a suspicious link.",
    "status": "OPEN"
  }
]
```

To retrieve a single incident, use `/api/incidents/1` or `/api/incidents/2`.
An unknown ID, such as `/api/incidents/3`, returns HTTP 404.

## Run tests

Open a terminal in the project folder and run:

```powershell
.\mvnw.cmd test
```

The project currently has five automated tests that check:

- The Spring application context loads.
- `GET /api/health` returns HTTP 200 with the response body `OK`.
- `GET /api/incidents/999` returns HTTP 404.
- `GET /api/incidents/1` returns HTTP 200 with the expected ID, title, description, and status.
- `GET /api/incidents` returns HTTP 200 and a JSON array containing two incidents with the expected IDs and titles.

The endpoint tests use MockMvc to simulate requests without starting a real web server.

## Current limitations

The incidents are created directly in `IncidentController`. The API does not yet support creating or changing incidents, database storage, or authentication. These are possible future steps.