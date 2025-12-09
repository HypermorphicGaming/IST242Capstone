package vehicles;

public class Vehicle {

    private String vin;
    private String make;
    private String model;
    private int year;
    private String type;
    private String vehicleType;
    private double costEstimate;

    public Vehicle(String vin, String make, String model, int year, String type, String vehicleType, double costEstimate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.vehicleType = vehicleType;
        this.costEstimate = costEstimate;
    }

    
}
