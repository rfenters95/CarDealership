package util;

import controllers.AlphaController;

public class Session {

    public static Employee sessionUser;
    public static Employee selectedEmployee;

    public static Customer selectedCustomer;

    public static Vehicle selectedVehicle;
    public static Vehicle tradeInVehicle;

    public static Invoice selectedInvoice;

    public static AlphaController alphaController;

    public static void clearSession() {

        Session.sessionUser = null;
        Session.selectedEmployee = null;
        Session.selectedCustomer = null;
        Session.selectedVehicle = null;
        Session.tradeInVehicle = null;
        Session.selectedInvoice = null;
        Session.alphaController = null;

    }
}
