package app.dtos;

import java.util.List;

public class ManagerDto extends NamedDto {
    private List<EmployeeDto> servants;

    public List<EmployeeDto> getServants() {
        return servants;
    }

    public void setServants(List<EmployeeDto> servants) {
        this.servants = servants;
    }
}
