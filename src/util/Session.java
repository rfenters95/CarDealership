package util;

import controllers.AlphaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Session {

    public Employee sessionUser;
    public Employee selectedEmployee;
    public Customer selectedCustomer;
    public Vehicle selectedVehicle;
    public Invoice selectedInvoice;
    public AlphaController alphaController;
    private String alertMessage;

    /*
    * Applies Singleton pattern to this class */
    private Session() {}
    private static Session instance = new Session();
    public static Session getInstance() {
        return instance;
    }

    /*
    * Display all entries from `Customers` table after add/remove from database */
    public void reloadCustomers() {
        alphaController.getSearchCustomerTabController().updateResultSet();
        alphaController.getSearchCustomerTabController().displayResultSet();
    }

    /*
    * Display all entries from `Employees` table after add/remove from database */
    public void reloadEmployees() {
        alphaController.getSearchEmployeeTabController().updateResultSet();
        alphaController.getSearchEmployeeTabController().displayResultSet();
    }

    /*
    * Display all entries from `Vehicles` table after add/remove from database */
    public void reloadVehicles() {
        alphaController.getSearchVehicleTabController().updateResultSet();
        alphaController.getSearchVehicleTabController().displayResultSet();
    }

    /*
    * Creates alert windows to alert users of problems or invalid inputs */
    public void alert(String alertMessage) {

        try {
            this.alertMessage = alertMessage;
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader fxmlLoader = new FXMLLoader();
            newStage.setTitle("Alert");

            fxmlLoader.setLocation(Session.class.getResource("../views/AlertBox.fxml"));
            Parent newResource = fxmlLoader.load();
            Scene newScene = new Scene(newResource);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * Clears values stored throughout user session */
    public void clearSession() {

        this.sessionUser = null;
        this.selectedEmployee = null;
        this.selectedCustomer = null;
        this.selectedVehicle = null;
        this.selectedInvoice = null;
        this.alphaController = null;
        this.alertMessage = null;

    }

    public String getAlertMessage() {
        return alertMessage;
    }

}
