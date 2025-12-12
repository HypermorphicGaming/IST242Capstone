package db;

import vehicles.Car;
import vehicles.Motorcycle;
import vehicles.Truck;
import vehicles.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:sqlite:./vehicle_maintenance.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static boolean upsertVehicle(Vehicle v) {
        String sqlVehicle = "INSERT OR REPLACE INTO vehicles (vin, make, model, year, type, vehicle_type, costEstimate) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sqlVehicle)) {
                ps.setString(1, v.getVin());
                ps.setString(2, v.getMake());
                ps.setString(3, v.getModel());
                ps.setInt(4, v.getYear());
                ps.setString(5, v.getType());
                ps.setString(6, v.getVehicleType());
                ps.setDouble(7, v.getCostEstimate());
                ps.executeUpdate();
            }

            if (v instanceof Car) {
                Car c = (Car) v;
                String sqlCar = "INSERT OR REPLACE INTO car_details (vin, numberOfDoors, oilChangeCost) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sqlCar)) {
                    ps.setString(1, c.getVin());
                    ps.setInt(2, c.getNumberOfDoors());
                    ps.setDouble(3, c.getOilChangeCost());
                    ps.executeUpdate();
                }
            } else if (v instanceof Truck) {
                Truck t = (Truck) v;
                String sqlTruck = "INSERT OR REPLACE INTO truck_details (vin, maxLoad, cargoInspectionCost) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sqlTruck)) {
                    ps.setString(1, t.getVin());
                    ps.setDouble(2, t.getMaxLoad());
                    ps.setDouble(3, t.getCargoInspectionCost());
                    ps.executeUpdate();
                }
            } else if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                String sqlMoto = "INSERT OR REPLACE INTO motorcycle_details (vin, chainCondition, chainReplacementCost) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sqlMoto)) {
                    ps.setString(1, m.getVin());
                    ps.setString(2, m.getChainCondition());
                    ps.setDouble(3, m.getChainReplacementCost());
                    ps.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteVehicle(String vin) {
        String delVehicle = "DELETE FROM vehicles WHERE vin = ?";
        String delCar = "DELETE FROM car_details WHERE vin = ?";
        String delTruck = "DELETE FROM truck_details WHERE vin = ?";
        String delMoto = "DELETE FROM motorcycle_details WHERE vin = ?";

        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(delCar)) {
                ps.setString(1, vin);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(delTruck)) {
                ps.setString(1, vin);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(delMoto)) {
                ps.setString(1, vin);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(delVehicle)) {
                ps.setString(1, vin);
                int affected = ps.executeUpdate();
                return affected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Vehicle loadVehicle(String vin) {
        String sql = "SELECT * FROM vehicles WHERE vin = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowToVehicle(conn, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Vehicle> loadAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY type, make, model";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    Vehicle v = rowToVehicle(conn, rs);
                    if (v != null) list.add(v);
                } catch (SQLException ex) {
                    System.err.println("Failed to load vehicle row: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static double averageCostByType(String type) {
        if (type == null) return 0.0;
        String t = type.trim().toLowerCase();
        String sql;

        switch (t) {
            case "car":
                sql = "SELECT AVG(v.costEstimate + IFNULL(c.oilChangeCost,0)) AS avgCost " +
                        "FROM vehicles v LEFT JOIN car_details c ON v.vin = c.vin " +
                        "WHERE LOWER(v.type) = ?";
                break;
            case "truck":
                sql = "SELECT AVG(v.costEstimate + IFNULL(t.cargoInspectionCost,0)) AS avgCost " +
                        "FROM vehicles v LEFT JOIN truck_details t ON v.vin = t.vin " +
                        "WHERE LOWER(v.type) = ?";
                break;
            case "motorcycle":
                sql = "SELECT AVG(v.costEstimate + IFNULL(m.chainReplacementCost,0)) AS avgCost " +
                        "FROM vehicles v LEFT JOIN motorcycle_details m ON v.vin = m.vin " +
                        "WHERE LOWER(v.type) = ?";
                break;
            default:
                return 0.0;
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double val = rs.getDouble("avgCost");
                    if (rs.wasNull()) return 0.0;
                    return val;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private static Vehicle rowToVehicle(Connection conn, ResultSet rs) throws SQLException {
        String vin = rs.getString("vin");
        String make = rs.getString("make");
        String model = rs.getString("model");
        int year = rs.getInt("year");
        String type = rs.getString("type");
        String vehicleType = rs.getString("vehicle_type");
        double cost = rs.getDouble("costEstimate");

        if (type == null) type = "";
        String t = type.trim().toLowerCase();

        switch (t) {
            case "car": {
                String sqlCar = "SELECT numberOfDoors, oilChangeCost FROM car_details WHERE vin = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlCar)) {
                    ps.setString(1, vin);
                    try (ResultSet drs = ps.executeQuery()) {
                        int doors = 4;
                        double oil = 0.0;
                        if (drs.next()) {
                            doors = drs.getInt("numberOfDoors");
                            oil = drs.getDouble("oilChangeCost");
                        }
                        return new Car(vin, make, model, year, type, vehicleType, cost, doors, oil);
                    }
                }
            }

            case "truck": {
                String sqlTruck = "SELECT maxLoad, cargoInspectionCost FROM truck_details WHERE vin = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlTruck)) {
                    ps.setString(1, vin);
                    try (ResultSet drs = ps.executeQuery()) {
                        double maxLoad = 0.0;
                        double cargoCost = 0.0;
                        if (drs.next()) {
                            maxLoad = drs.getDouble("maxLoad");
                            cargoCost = drs.getDouble("cargoInspectionCost");
                        }
                        return new Truck(vin, make, model, year, type, vehicleType, cost, maxLoad, cargoCost);
                    }
                }
            }

            case "motorcycle": {
                String sqlMoto = "SELECT chainCondition, chainReplacementCost FROM motorcycle_details WHERE vin = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlMoto)) {
                    ps.setString(1, vin);
                    try (ResultSet drs = ps.executeQuery()) {
                        String chainCond = "OK";
                        double chainCost = 0.0;
                        if (drs.next()) {
                            chainCond = drs.getString("chainCondition");
                            chainCost = drs.getDouble("chainReplacementCost");
                        }
                        return new Motorcycle(vin, make, model, year, type, vehicleType, cost, chainCond, chainCost);
                    }
                }
            }

            default:
                return new Car(vin, make, model, year, type, vehicleType, cost, 4, 0.0);
        }
    }
}