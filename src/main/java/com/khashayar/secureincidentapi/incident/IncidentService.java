package com.khashayar.secureincidentapi.incident;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class IncidentService {

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

    public Optional<Incident> getIncidentById(Long id) {
        for (Incident incident : getIncidents()) {
            if (incident.getId().equals(id)) {
                return Optional.of(incident);
            }
        }

        return Optional.empty();
    }
}