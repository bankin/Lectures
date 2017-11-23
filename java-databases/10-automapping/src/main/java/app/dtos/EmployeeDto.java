package app.dtos;

import java.math.BigDecimal;

public class EmployeeDto extends NamedDto {

    private BigDecimal salary;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
