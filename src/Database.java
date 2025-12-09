import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    public static void update(String table, String vinPort){
        try (Connection conn = DriverManager.getConnection(url)){
            String sql = "UPDATE "+ table + " WHERE vin = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void delete(String table, String vinPort) {
        try (Connection connection = DriverManager.getConnection(url)){

            String sql = "DELETE FROM "+ table + " WHERE vin = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, vinPort);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
