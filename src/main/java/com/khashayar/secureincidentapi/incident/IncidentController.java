package com.khashayar.secureincidentapi.incident;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {

    @GetMapping("/api/incidents")
    public List<Incident> getIncidents() {
        Incident firstIncident = new Incident(
                1L,
                "Suspicious login",
                "Several failed login attempts were detected.",
                "OPEN"
        );

        Incident secondIncident = new Incident(
                2L,
                "Phishing email",
                "An employee reported an email containing a suspicious link.",
                "OPEN"
        );

        return List.of(firstIncident, secondIncident);
    }

    @GetMapping("/api/incidents/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        for (Incident incident : getIncidents()) {
            if (incident.getId().equals(id)) {
                return ResponseEntity.ok(incident);
            }
        }

        return ResponseEntity.notFound().build();
    }
}