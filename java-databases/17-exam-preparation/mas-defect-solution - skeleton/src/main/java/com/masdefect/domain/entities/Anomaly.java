package com.masdefect.domain.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Anomaly implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = Planet.class, optional = false)
    private Planet originPlanet;

    @ManyToOne(targetEntity = Planet.class, optional = false)
    private Planet teleportPlanet;

    @ManyToMany(targetEntity = Person.class)
    private Set<Person> victims;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Planet getOriginPlanet() {
        return originPlanet;
    }

    public void setOriginPlanet(Planet originPlanet) {
        this.originPlanet = originPlanet;
    }

    public Planet getTeleportPlanet() {
        return teleportPlanet;
    }

    public void setTeleportPlanet(Planet teleportPlanet) {
        this.teleportPlanet = teleportPlanet;
    }

    public Set<Person> getVictims() {
        return victims;
    }

    public void setVictims(Set<Person> victims) {
        this.victims = victims;
    }
}
