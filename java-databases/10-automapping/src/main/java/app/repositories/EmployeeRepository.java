package app.repositories;

import app.entites.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByBirthDateBeforeOrderBySalaryDesc(LocalDate before);
}
