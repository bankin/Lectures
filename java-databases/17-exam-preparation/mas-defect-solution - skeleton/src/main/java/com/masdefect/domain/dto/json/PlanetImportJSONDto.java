package com.masdefect.domain.dto.json;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PlanetImportJSONDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private String sun;

    @Expose
    private String solarSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(String solarSystem) {
        this.solarSystem = solarSystem;
    }
}
