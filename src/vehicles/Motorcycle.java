package vehicles;

public class Motorcycle extends Vehicle {

    private String chainCondition;
    private double chainReplacementCost;

    public Motorcycle(String vin, String make, String model, int year, String type, String vehicleType, double costEstimate, String chainCondition, double chainReplacementCost) {
        super(vin, make, model, year, type, vehicleType, costEstimate);
        this.chainCondition = chainCondition;
        this.chainReplacementCost = chainReplacementCost;
    }

    public String getChainCondition() {
        return chainCondition;
    }
    public double getChainReplacementCost() {
        return chainReplacementCost;
    }
    public void setChainCondition(String chainCondition) {
        this.chainCondition = chainCondition;
    }
    public void setChainReplacementCost(double chainReplacementCost) {
        this.chainReplacementCost = chainReplacementCost;
    }

    @Override
    public String displayMaintenanceDetails() {
        double totalCost = costEstimate + chainReplacementCost;
        return "Motorcycle Maintenance:\n" +
                "Chain Condition: " + chainCondition + "\n" +
                "Chain Replacement Cost: $" + chainReplacementCost + "\n" +
                "Estimated Total Cost: $" + totalCost;
    }
}