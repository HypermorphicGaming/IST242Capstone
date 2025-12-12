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

    public String getVin() { return vin; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getType() { return type; }
    public String getVehicleType() { return vehicleType; }
    public double getCostEstimate() { return costEstimate; }

    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setType(String type) { this.type = type; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setCostEstimate(double costEstimate) { this.costEstimate = costEstimate; }

    public abstract String displayMaintenanceDetails();
}