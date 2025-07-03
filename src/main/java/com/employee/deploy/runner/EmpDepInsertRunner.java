package com.employee.deploy.runner;

import com.employee.deploy.entity.Department;
import com.employee.deploy.entity.Employee;
import com.employee.deploy.repository.DepartmentRepository;
import com.employee.deploy.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("local")
@Slf4j
public class EmpDepInsertRunner implements ApplicationRunner {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        try {
            List<Department> departments = createInitialDepartments();
            List<Employee> employees = createInitialEmployees(departments);

            departmentRepository.saveAll(departments);
            employeeRepository.saveAll(employees);

            log.info("Successfully inserted {} departments and {} employees",
                    departments.size(), employees.size());
        } catch (Exception e) {
            log.error("Failed to initialize sample data", e);
            throw new DataInitializationException("Failed to initialize sample data", e);
        }
    }

    private List<Department> createInitialDepartments() {
        return List.of(
                Department.builder()
                        .departmentName("HR")
                        .departmentDescription("Performs human resource management functions")
                        .build(),

                Department.builder()
                        .departmentName("Marketing")
                        .departmentDescription("Creates strategies for selling company products")
                        .build(),

                Department.builder()
                        .departmentName("Sales")
                        .departmentDescription("Identifies sales goals and prepares sales plans")
                        .build()
        );
    }

    private List<Employee> createInitialEmployees(List<Department> departments) {
        return List.of(
                Employee.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .email("john@company.com")
                        .department(departments.get(0)) // HR
                        .build(),

                Employee.builder()
                        .firstName("Sarah")
                        .lastName("Johnson")
                        .email("sarah@company.com")
                        .department(departments.get(1)) // Marketing
                        .build(),

                Employee.builder()
                        .firstName("Emily")
                        .lastName("Brown")
                        .email("emily@company.com")
                        .department(departments.get(2)) // Sales
                        .build()
        );
    }

    public static class DataInitializationException extends RuntimeException {
        public DataInitializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}