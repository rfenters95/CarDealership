package util.vehiclecomparators;

import util.Vehicle;

import java.util.Comparator;

public class YearComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getYear().compareTo(o2.getYear());
    }
}
