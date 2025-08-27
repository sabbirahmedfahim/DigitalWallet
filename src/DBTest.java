import db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connected to MySQL successfully!");
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}