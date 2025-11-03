import java.sql.*;
import java.util.*;

public class EmployeeDAO {

    // CREATE: Adds a new employee to the database
    public static int addEmployee(Employee e) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO employees (id, name, designation, salary) VALUES(?,?,?,?)")) {

            ps.setInt(1, e.id);
            ps.setString(2, e.name);
            ps.setString(3, e.designation);
            ps.setDouble(4, e.salary);
            status = ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    // READ: Retrieves all employees from the database
    public static List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                list.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // UPDATE: Updates an existing employee record (Corrected Logic)
    public static int updateEmployee(Employee e) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             // SQL placeholders: name(1), designation(2), salary(3), ID(4)
             PreparedStatement ps = con.prepareStatement("UPDATE employees SET name=?, designation=?, salary=? WHERE id=?")) {

            ps.setString(1, e.name);
            ps.setString(2, e.designation);
            ps.setDouble(3, e.salary);
            ps.setInt(4, e.id); // Used in the WHERE clause

            status = ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }
    
    // DELETE: Deletes an employee by ID
    public static int deleteEmployee(int id) {
        int status = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM employees WHERE id=?")) {

            ps.setInt(1, id);
            status = ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }
}