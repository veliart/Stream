package ru.skypro.ListsAndSets.sevices.impl;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.skypro.ListsAndSets.exception.EmployeeAlreadyAddedException;
import ru.skypro.ListsAndSets.exception.EmployeeNotFoundException;
import ru.skypro.ListsAndSets.exception.EmployeeStorageIsFullException;
import ru.skypro.ListsAndSets.exception.InvalidNameException;
import ru.skypro.ListsAndSets.model.Employee;
import ru.skypro.ListsAndSets.sevices.EmployeeService;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final int STORAGE_SIZE = 100;
    @PostConstruct
    public void initEmployees() {
        add("Ivan", "Ivanov", 10000, 1);
        add("Ivan1", "Ivanov1", 20000, 1);
        add("Ivan2", "Ivanov2", 30000, 1);
        add("Petr", "Petrov", 20000, 2);
        add("Petr2", "Petrov2", 30000, 2);
        add("Petr3", "Petrov3", 40000, 2);
        add("Sidor", "Sidorov", 30000, 3);
    }
    private final Map<String, Employee> employees = new HashMap<>();

    @Override
    public Employee add(String firstName, String lastName, Integer salary, Integer department) {
        if (employees.size() >= STORAGE_SIZE) {
            throw new EmployeeStorageIsFullException("Превышено количество сотрудников в " + STORAGE_SIZE + " чел.");
        }
        if (employees.containsKey(getKey(firstName, lastName))) {
            throw new EmployeeAlreadyAddedException("Сотрудник" + firstName + " " + lastName + " уже работает в компании");
        }
        validateNames(firstName, lastName);
        Employee employee = new Employee(StringUtils.capitalize(firstName), StringUtils.capitalize(lastName), salary, department);
        employees.put(getKey(employee), employee);
        return employee;
    }

    @Override
    public Employee remove(String firstName, String lastName) {
        if (!employees.containsKey(getKey(firstName, lastName))) {
            throw new EmployeeAlreadyAddedException("Сотрудник" + firstName + " " + lastName + " не найден в списке.");
        }
        return employees.remove(getKey(firstName, lastName));
    }

    @Override
    public Employee find(String firstName, String lastName) {
        Employee employee = employees.get(getKey(firstName, lastName));
        if (employee == null) {
            throw new EmployeeNotFoundException("Сотрудник " + firstName + " " + lastName +
                    " не найден.");
        }
        return employee;
    }
    @Override
    public Map<String, Employee> getAll() {
        return Collections.unmodifiableMap(employees);
    }
    private static String getKey(String firstName, String lastName) {
        return firstName + lastName;
    }
    private static String getKey(Employee employee) {
        return employee.getFirstName() + employee.getLastName();
    }

    private void validateNames(String... names) {
        for (String name: names) {
            if (!StringUtils.isAlpha(name)) {
                throw new InvalidNameException(name + " is invalid");
            }
        }
    }
}
