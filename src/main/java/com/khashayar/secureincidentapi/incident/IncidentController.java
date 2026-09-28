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
        Incident example = new Incident(
                1L,
                "Mistenkelig innlogging",
                "Flere mislykkede innloggingsforsøk ble registrert.",
                "OPEN"
        );

        return List.of(example);
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