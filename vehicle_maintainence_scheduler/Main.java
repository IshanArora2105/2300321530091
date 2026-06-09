import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Vehicle> vehicles =new ArrayList<>();

        vehicles.add(
                new Vehicle("A",5,8)
        );

        vehicles.add(
                new Vehicle("B",4,7)
        );

        vehicles.add(
                new Vehicle("C",7,10)
        );

        int maxImpact =
                Scheduler.getMaxImpact(vehicles,10 );

        System.out.println(
                "Maximum Impact = "+ maxImpact
        );
    }
}
