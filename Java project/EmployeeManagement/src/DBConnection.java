import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db",  // <-- change this to your DB name
                "root",                                    // <-- your MySQL username
                "2007"                       // <-- your MySQL password
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
