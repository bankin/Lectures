package com.masdefect.domain.dto.json;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class AnomalyExportJSONDto implements Serializable {

    @Expose
    private Long id;

    @Expose
    private PlanetExportJSONDto originPlanet;

    @Expose
    private PlanetExportJSONDto teleportPlanet;

    @Expose
    private Long victimsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanetExportJSONDto getOriginPlanet() {
        return originPlanet;
    }

    public void setOriginPlanet(PlanetExportJSONDto originPlanet) {
        this.originPlanet = originPlanet;
    }

    public PlanetExportJSONDto getTeleportPlanet() {
        return teleportPlanet;
    }

    public void setTeleportPlanet(PlanetExportJSONDto teleportPlanet) {
        this.teleportPlanet = teleportPlanet;
    }

    public Long getVictimsCount() {
        return victimsCount;
    }

    public void setVictimsCount(Long victimsCount) {
        this.victimsCount = victimsCount;
    }
}
