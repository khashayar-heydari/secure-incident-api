# Secure Incident Tracker API

A small Java and Spring Boot project for learning how to build a REST API for security incidents.

The current version supports creating and retrieving incidents. Data is stored in memory while the application runs. Each restart restores the two example incidents and removes any incidents created during the previous run.

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

To try the API using the commands below, keep the application running and open a second PowerShell terminal.

## API endpoints

| Method | Path | Current response |
| --- | --- | --- |
| GET | `/api/health` | HTTP 200 with the text `OK` |
| GET | `/api/incidents` | HTTP 200 with all incidents |
| GET | `/api/incidents/{id}` | HTTP 200 with the matching incident, or HTTP 404 if it does not exist |
| POST | `/api/incidents` | HTTP 201 with the created incident and a `Location` header |

## Retrieve incidents

Run:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/incidents" -Method Get
```

Immediately after startup, the API returns these two example incidents:

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

To retrieve a single incident, use its ID:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/incidents/1" -Method Get
```

An ID that does not exist returns HTTP 404.

## Create an incident

Send a JSON object containing a title and description:

```powershell
Invoke-WebRequest -UseBasicParsing -Uri "http://localhost:8080/api/incidents" -Method Post -ContentType "application/json" -Body '{"title":"Malware detected","description":"Antivirus detected a suspicious file."}'
```

The API assigns an ID and sets the initial status to `OPEN`.

For the first incident created after startup, the response is HTTP 201 with this body:

```json
{
  "id": 3,
  "title": "Malware detected",
  "description": "Antivirus detected a suspicious file.",
  "status": "OPEN"
}
```

The response includes the header `Location: /api/incidents/3`.

Retrieve that incident with:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/incidents/3" -Method Get
```

Each POST request creates another incident with a new ID. Created incidents also appear in `GET /api/incidents`.

## Code structure

- `Incident` represents an incident with an ID, title, description, and status.
- `CreateIncidentRequest` holds the title and description from a creation request.
- `IncidentController` handles HTTP requests and responses for the incident endpoints.
- `IncidentService` stores incidents in memory, looks them up by ID, and creates new incidents.
- `HealthController` provides the health endpoint.

`IncidentController` receives an `IncidentService` through constructor injection. This separates HTTP handling from incident logic.

## Run tests

Open a terminal in the project folder and run:

```powershell
.\mvnw.cmd test
```

The project currently has six automated tests that check:

- The Spring application context loads.
- `GET /api/health` returns HTTP 200 with the response body `OK`.
- An unknown incident ID returns HTTP 404.
- An existing incident returns HTTP 200 with the expected data.
- The initial incident list contains the two expected example incidents.
- Creating an incident returns HTTP 201, the expected data, and a `Location` header, and the incident can then be retrieved by ID.

The endpoint tests use MockMvc to simulate requests without starting a real web server.

The creation test uses `@DirtiesContext` so its changes to the in-memory data do not affect subsequent tests.

## Current limitations

- Data is stored only in memory and is lost when the application stops.
- The application starts with two hardcoded example incidents.
- Updating and deleting incidents are not implemented.
- Titles and descriptions are not yet validated.
- Authentication and authorization are not implemented.

Database storage, input validation, and security are planned future steps.