package util.vehiclecomparators;

import util.Vehicle;

import java.util.Comparator;

public class ModelComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getModel().compareTo(o2.getModel());
    }
}
