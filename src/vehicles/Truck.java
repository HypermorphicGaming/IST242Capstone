package vehicles;
import java.lang.*;

public class Truck extends Vehicle {
    private double maxLoad;
    private double cargoInspectionCost;

    public Truck(String vin, String make, String model, int year, String type, String vehicleType, double costEstimate, double maxLoad, double cargoInspectionCost) {
        super(vin, make, model, year, type, vehicleType, costEstimate);
        this.maxLoad = maxLoad;
        this.cargoInspectionCost = cargoInspectionCost;
    }

    @Override
    public String displayMaintenanceDetails() {
        return "Truck Maintenance:/n" + "Max Load: " + maxLoad + "/n" + "Cargo Inspection Cost: $" + cargoInspectionCost + "/n" + "Estimated Total Cost: $" + costEstimate;
    }
}
