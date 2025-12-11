package vehicles;

public class Car extends Vehicle {

    private int numberOfDoors;
    private double oilChangeCost;

    public Car(String vin, String make, String model, int year, String type, String vehicleType, double costEstimate, int numberOfDoors, double oilChangeCost ) {
        super(vin, make, model, year, type, vehicleType, costEstimate);
        this.numberOfDoors = numberOfDoors;
        this.oilChangeCost = oilChangeCost;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }
    public double getOilChangeCost() {
        return oilChangeCost;
    }
    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }
    public void setOilChangeCost(double oilChangeCost) {
        this.oilChangeCost = oilChangeCost;
    }

    @Override
    public String displayMaintenanceDetails() {
        double totalCost = costEstimate + oilChangeCost;
        return "Car Maintenance:\n" +
                "Number of Doors: " + numberOfDoors + "\n" +
                "Oil Change Cost: $" + oilChangeCost + "\n" +
                "Estimated Total Cost: $" + totalCost;
    }
}