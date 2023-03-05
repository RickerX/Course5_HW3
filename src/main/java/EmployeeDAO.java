import java.util.List;

public interface EmployeeDAO {
    // добавление объекта
    void create(Employee employee);

    // получение объекта по id
    Employee readById(int id);

    // получение списка всех объектов
    List<Employee> readAll();

    // изменение объекта по id
    void updateEmployeeById(int id, String lastName, int city);

    // удаление оюъекта по id
    void deleteById(int id);
}
