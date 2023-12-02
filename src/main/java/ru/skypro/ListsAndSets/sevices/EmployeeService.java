package ru.skypro.ListsAndSets.sevices;

import ru.skypro.ListsAndSets.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee add(String firstName, String lastName);
    Employee remove(String firstName, String lastName);
    Employee find(String firstName, String lastName);
    Map<String, Employee> getAll();
}
