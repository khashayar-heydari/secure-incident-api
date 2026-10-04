package com.khashayar.secureincidentapi.incident;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class IncidentService {

    private final List<Incident> incidents = new ArrayList<>();
    private long nextId = 3L;

    public IncidentService() {
        incidents.add(new Incident(
                1L,
                "Suspicious login",
                "Several failed login attempts were detected.",
                "OPEN"
        ));

        incidents.add(new Incident(
                2L,
                "Phishing email",
                "An employee reported an email containing a suspicious link.",
                "OPEN"
        ));
    }

    public synchronized List<Incident> getIncidents() {
        return List.copyOf(incidents);
    }

    public synchronized Optional<Incident> getIncidentById(Long id) {
        for (Incident incident : incidents) {
            if (incident.getId().equals(id)) {
                return Optional.of(incident);
            }
        }

        return Optional.empty();
    }

    public synchronized Incident createIncident(
            String title,
            String description
    ) {
        Incident incident = new Incident(
                nextId,
                title,
                description,
                "OPEN"
        );

        nextId++;
        incidents.add(incident);

        return incident;
    }
}