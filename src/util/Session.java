package util;

public class Session {

    public static Employee sessionUser;
    public static Employee selectedEmployee;
    public static Customer selectedCustomer;
    public static Vehicle selectedVehicle;
    public static Invoice selectedInvoice;

    public static void clearSession() {

        Session.sessionUser = null;
        Session.selectedEmployee = null;
        Session.selectedCustomer = null;
        Session.selectedVehicle = null;

    }
}
