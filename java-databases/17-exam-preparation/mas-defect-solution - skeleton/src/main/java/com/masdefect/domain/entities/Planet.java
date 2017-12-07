package com.masdefect.domain.entities;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Planet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(targetEntity = Star.class)
    private Star sun;

    @ManyToOne(targetEntity = SolarSystem.class)
    private SolarSystem solarSystem;

    @OneToMany(mappedBy = "homePlanet")
    private Set<Person> people;

    @OneToMany(mappedBy = "originPlanet")
    private Set<Anomaly> anomaliesOrigin;

    @OneToMany(mappedBy = "teleportPlanet")
    private Set<Anomaly> anomaliesTeleport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Star getSun() {
        return sun;
    }

    public void setSun(Star sun) {
        this.sun = sun;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public Set<Anomaly> getAnomaliesOrigin() {
        return anomaliesOrigin;
    }

    public void setAnomaliesOrigin(Set<Anomaly> anomaliesOrigin) {
        this.anomaliesOrigin = anomaliesOrigin;
    }

    public Set<Anomaly> getAnomaliesTeleport() {
        return anomaliesTeleport;
    }

    public void setAnomaliesTeleport(Set<Anomaly> anomaliesTeleport) {
        this.anomaliesTeleport = anomaliesTeleport;
    }
}
