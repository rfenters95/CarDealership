package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import util.Session;
import util.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;

public class VehicleDetailsController implements Initializable {

    @FXML private TextField makeTF;
    @FXML private TextField modelTF;
    @FXML private TextField yearTF;
    @FXML private TextField colorTF;
    @FXML private TextField typeTF;
    @FXML private TextField priceTF;

    /*
    @FXML public void viewInvoice(ActionEvent event) {
        
    }
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Vehicle vehicle = Session.selectedVehicle;

        makeTF.setText(vehicle.getMake());
        makeTF.setDisable(true);

        modelTF.setText(vehicle.getModel());
        modelTF.setDisable(true);

        yearTF.setText(vehicle.getYear());
        yearTF.setDisable(true);

        colorTF.setText(vehicle.getColor());
        colorTF.setDisable(true);

        typeTF.setText(vehicle.getType());
        typeTF.setDisable(true);

        priceTF.setDisable(true);

    }
}
