package softuni.judgesystem.dtos.json;

import com.google.gson.annotations.Expose;

public class StrategyDto {

    @Expose
    private Long id;

    @Expose
    private String name;

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
}
