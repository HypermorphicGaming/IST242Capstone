package UI;

public class calculateCost {

    public static double getAverageCost(List<Vehicle> vehicles, String type){
        double total = 0;
        int count = 0;

        for(Vehicle v: vehicles){
            if (V.getType().equalsIgnoreCase(type)){
                total += v.getMainteanceCost();
                count++;
            }
        }
        if(count = 0 )
            return 0;
        return total/ count;
    }
}
