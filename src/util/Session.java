package util;

import controllers.AlphaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Session {

    public static Employee sessionUser;
    public static Employee selectedEmployee;
    public static Customer selectedCustomer;
    public static Vehicle selectedVehicle;
    public static Invoice selectedInvoice;

    public static AlphaController alphaController;

    private static String alertMessage;

    public static String getAlertMessage() {
        return alertMessage;
    }

    public static void alert(String alertMessage) {

        try {
            Session.alertMessage = alertMessage;
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

    public static void clearSession() {

        Session.sessionUser = null;
        Session.selectedEmployee = null;
        Session.selectedCustomer = null;
        Session.selectedVehicle = null;
        Session.selectedInvoice = null;
        Session.alphaController = null;
        Session.alertMessage = null;

    }
}
