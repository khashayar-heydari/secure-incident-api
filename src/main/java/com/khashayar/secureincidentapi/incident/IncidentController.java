package com.khashayar.secureincidentapi.incident;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
}
