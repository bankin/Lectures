package entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

/**
 * Created by Simona Simeonova on 10/31/2017.
 */

@Entity(name = "example_entities")
public class ExampleEntity {
    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "town")
    private String town;

    @Column(name = "country")
    private String conutry;

    public ExampleEntity(String fullName, String town){
        this.fullName = fullName;
        this.town = town;
    }

}
