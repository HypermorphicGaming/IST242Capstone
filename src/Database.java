import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String url = "jdbc:sqlite:./vehicle_maintenance.db";


    public void accessDatabase(){
        try (Connection connection = DriverManager.getConnection(url)){
            System.out.println("Connected to Database");
        }catch (SQLException e){
            System.err.println("Database error: "+ e.getMessage());
        }catch (Exception e){
            System.err.println("Error importing schema: " + e.getMessage());
        }
    }
    public static void insert(){

    }
    public static void update(){

    }
    public static void delete(){

    }

}
