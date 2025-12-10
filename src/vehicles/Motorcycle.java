package vehicles;
import java.lang.*;

public class Motorcycle extends Vehicle {

    private String chainCondition;
    private double chainReplacementCost;

    public Motorcycle(String vin, String make, String model, int year, String type, String vehicleType, double costEstimate, String chainCondition, double chainReplacementCost) {
        super(vin, make, model, year, type, vehicleType, costEstimate);
        this.chainCondition = chainCondition;
        this.chainReplacementCost = chainReplacementCost;
    }

    public double setCostEstimate() {
        costEstimate += chainReplacementCost;
    }

    @Override
    public String displayMaintenanceDetails() {
        return "Motorcycle Maintenance:/n" + "Chain Condition: " + chainCondition + "/n" + "Chain Replacement Cost: $" + chainReplacementCost + "/n" + "Estimated Total Cost: $" + costEstimate;
    }
}