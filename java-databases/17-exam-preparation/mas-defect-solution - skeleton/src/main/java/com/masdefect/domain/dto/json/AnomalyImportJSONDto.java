package com.masdefect.domain.dto.json;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class AnomalyImportJSONDto implements Serializable {

    @Expose
    private String originPlanet;

    @Expose
    private String teleportPlanet;

    public String getOriginPlanet() {
        return originPlanet;
    }

    public void setOriginPlanet(String originPlanet) {
        this.originPlanet = originPlanet;
    }

    public String getTeleportPlanet() {
        return teleportPlanet;
    }

    public void setTeleportPlanet(String teleportPlanet) {
        this.teleportPlanet = teleportPlanet;
    }
}
