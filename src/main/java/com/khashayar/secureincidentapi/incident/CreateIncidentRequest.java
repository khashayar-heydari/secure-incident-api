package com.khashayar.secureincidentapi.incident;

public class CreateIncidentRequest {

    private String title;
    private String description;

    public CreateIncidentRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
