package softuni.judgesystem.dtos.json;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ContestDto {
    @Expose
    private Long id;

    @Expose
    private String name;

    @Expose
    private CategoryDto category;

    @Expose
    private List<StrategyDto> allowedStrategies;

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

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public List<StrategyDto> getAllowedStrategies() {
        return allowedStrategies;
    }

    public void setAllowedStrategies(List<StrategyDto> allowedStrategies) {
        this.allowedStrategies = allowedStrategies;
    }
}
