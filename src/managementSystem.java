import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class managementSystem {

    public static void main(String[] args) {
        Database db = new Database();
        db.accessDatabase();
    }
}