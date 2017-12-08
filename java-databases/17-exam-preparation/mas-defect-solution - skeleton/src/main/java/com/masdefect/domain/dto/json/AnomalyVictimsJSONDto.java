package com.masdefect.domain.dto.json;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class AnomalyVictimsJSONDto implements Serializable {

    @Expose
    private Long id;

    @Expose
    private String person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
