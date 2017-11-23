package app;

import app.dtos.EmployeeDto;
import app.dtos.ManagerDto;
import app.entites.Employee;
import app.repositories.AddressRepository;
import app.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ConsoleRunner implements CommandLineRunner {

    private AddressRepository addressRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public ConsoleRunner(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Employee one = this.employeeRepository.findOne(1L);

        ModelMapper mapper = new ModelMapper();
        EmployeeDto dto = mapper.map(one, EmployeeDto.class);

        System.out.println(dto.getFirstName());

        ManagerDto managerDto = mapper.map(one, ManagerDto.class);

        for (EmployeeDto e : managerDto.getServants()) {
            System.out.printf("- %s %s %.2f \n",
                    e.getFirstName(), e.getLastName(), e.getSalary());
        }

        List<Employee> employees = this.employeeRepository
                .findByBirthDateBeforeOrderBySalaryDesc(
                    LocalDate.parse("1990-01-01"));

        for (Employee e : employees) {
            System.out.println(e.getFirstName());
            String managerName = e.getManager() == null ? "[no manager]" : e.getManager().getFirstName();
            System.out.println("Manager: " + managerName);
        }
    }
}
