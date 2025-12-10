package vehicles;

public abstract class Vehicle {

    protected final String vin;
    protected String make;
    protected String model;
    protected int year;
    protected String type;
    protected String vehicleType;
    protected double costEstimate;

    public Vehicle(String vin, String make, String model, int year, String type, String vehicleType, double costEstimate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.vehicleType = vehicleType;
        this.costEstimate = costEstimate;
    }

    public abstract String displayMaintenanceDetails();

    public double displayCostEstimate() {
        return costEstimate;
    }

}
