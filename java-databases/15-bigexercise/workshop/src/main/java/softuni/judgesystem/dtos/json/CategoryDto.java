package softuni.judgesystem.dtos.json;

//"id": "1",
//        "name": "Programming Basics",
//        "category": null,
//        "categories": [

import com.google.gson.annotations.Expose;

import java.util.Set;

public class CategoryDto {
    @Expose
    private Long id;

    @Expose
    private String name;

    @Expose
    private CategoryDto category;

    @Expose
    private Set<CategoryDto> categories;

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

    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDto> categories) {
        this.categories = categories;
    }
}
