# Secure Incident Tracker API

A small Java and Spring Boot project for learning how to build a REST API for security incidents.

The current version provides a health endpoint and returns one fixed example incident. Incidents are not stored in a database yet.

## Requirements

- Java 21
- Windows with PowerShell

The project includes a Maven Wrapper, so a separate Maven installation is not required.

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
| GET | `/api/incidents` | A list containing one fixed example incident |
| GET | `/api/incidents/{id}` | The incident with that ID, or 404 if it does not exist |

Example response from `GET /api/incidents`:

```json
[
  {
    "id": 1,
    "title": "Suspicious login",
    "description": "Several failed login attempts were detected.",
    "status": "OPEN"
  }
]
```

## Run tests

```powershell
.\mvnw.cmd test
```

## Current limitations

The incident is created directly in `IncidentController`. The API does not yet support creating or changing incidents, database storage, or authentication. These are possible future steps.