package softuni.judgesystem.dtos.json;

import com.google.gson.annotations.Expose;

public class UserDto {
    @Expose
    private Long id;

    @Expose
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
