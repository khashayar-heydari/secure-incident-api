package com.khashayar.secureincidentapi.incident;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping("/api/incidents")
    public List<Incident> getIncidents() {
        return incidentService.getIncidents();
    }

    @GetMapping("/api/incidents/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.of(incidentService.getIncidentById(id));
    }

    @PostMapping("/api/incidents")
    public ResponseEntity<Incident> createIncident(
            @RequestBody CreateIncidentRequest request
    ) {
        Incident incident = incidentService.createIncident(
                request.getTitle(),
                request.getDescription()
        );

        URI location = URI.create("/api/incidents/" + incident.getId());

        return ResponseEntity.created(location).body(incident);
    }
}