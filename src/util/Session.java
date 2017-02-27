package util;

public class Session {
    public static Employee employee;
    public static Customer customer;
    public static Vehicle vehicle;
    public static void clearSession() {
        Session.employee = null;
        Session.customer = null;
        Session.vehicle = null;
    }
}
