import db.DBConnection;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("SUCCESS: Database connected!");
            DBConnection.closeConnection(conn);
        } else {
            System.out.println("FAILED: Could not connect to database");
        }
    }
}
