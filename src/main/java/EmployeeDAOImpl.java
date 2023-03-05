import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EmployeeDAOImpl implements EmployeeDAO {
    private final Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // добавление объекта
    @Override
    public void create(Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO employee (first_name,last_name,gender,age,city_id) VALUES ((?),(?),(?),(?),(?))")) {
            statement.setString(1, employee.getFirst_name());
            statement.setString(2, employee.getLast_name());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCity().getCity_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // получение объекта по id
    @Override
    public Employee readById(int id) {
        Employee employee = new Employee();
        try (PreparedStatement statement = connection.prepareStatement("SELECT*FROM employee INNER JOIN city ON employee.city_id = city.city_id AND id = (?)")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employee.setId(Integer.parseInt(resultSet.getString("id")));
                employee.setFirst_name(resultSet.getString("first_name"));
                employee.setLast_name(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(Integer.parseInt(resultSet.getString("age")));
                employee.setCity(new City(resultSet.getInt("city_id"), resultSet.getString("city_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    // получение списка всех объектов
    @Override
    public List<Employee> readAll() {
        List<Employee> employeeList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT*FROM employee INNER JOIN city ON employee.city_id = city.city_id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = Integer.parseInt(resultSet.getString("age"));
                City city = new City(resultSet.getInt("city_id"), resultSet.getString("city_name"));
                employeeList.add(new Employee(id, firstName, lastName, gender, age, city));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    // изменение объекта по id
    @Override
    public void updateEmployeeById(int id, String lastName, int city) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE employee SET last_name = (?), city_id = (?) WHERE id = (?)")) {
            statement.setString(1, lastName);
            statement.setInt(2, city);
            statement.setInt(3, id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // удаление оюъекта по id
    @Override
    public void deleteById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE id = (?)")) {
            statement.setInt(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
