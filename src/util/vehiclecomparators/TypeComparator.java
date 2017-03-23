package util.vehiclecomparators;

import util.Vehicle;

import java.util.Comparator;

public class TypeComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getType().compareTo(o2.getType());
    }
}
