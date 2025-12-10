package vehicles;
import java.lang.*;

public class Car extends Vehicle {

    private int numberOfDoors;
    private double oilChangeCost;

    public Car(String vin, String make, String model, int year, String type, String vehicleType, double costEstimate, int numberOfDoors, double oilChangeCost ) {
        super(vin, make, model, year, type, vehicleType, costEstimate);
        this.numberOfDoors = numberOfDoors;
        this.oilChangeCost = oilChangeCost;
    }

    @Override
    public String displayMaintenanceDetails() {
        return "Car Maintenance:/n" +
                "Number of Doors: " + numberOfDoors + "/n" +
                "Oil Change Cost: $" + oilChangeCost + "/n" +
                "Estimated Total Cost: $" + costEstimate;
    }
}
