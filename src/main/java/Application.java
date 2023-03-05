import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws SQLException {

        final String user = "postgres";
        final String password = "030179";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        try (final Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = (?)")){

            statement.setInt(1, 6);


            final ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {

                String firstName = "First_name: " + resultSet.getString("first_name");
                String lastName = "Last_name: " + resultSet.getString("last_name");
                String gender = "Gender: " + resultSet.getString("gender");
                String city_id = "City_id: " + resultSet.getString("city_id");


                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(gender);
                System.out.println(city_id);
            }
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);
            City city = new City(1, "Tel-Aviv");
            Employee employee1 = new Employee(9, "Inna", "Rapaport", "female", 46, new City(2, "Tel-Aviv"));
            employeeDAO.create(employee1);
            System.out.println(employee1);
            employeeDAO.readById(9);
            List<Employee> list = new ArrayList<>(employeeDAO.readAll());
            for (Employee employee : list) {
                System.out.println(employee);
            }
        }
    }
}


