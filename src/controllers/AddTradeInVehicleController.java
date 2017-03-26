package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTradeInVehicleController implements Initializable {

    @FXML private TextField makeTF;
    @FXML private TextField modelTF;
    @FXML private TextField yearTF;
    @FXML private TextField colorTF;
    @FXML private ComboBox<String> typeCB;
    @FXML private TextField priceTF;

    @FXML public void save(ActionEvent event) {

        try {

            Vehicle vehicle = new Vehicle(makeTF, modelTF, yearTF, colorTF, typeCB, priceTF);
            vehicle.insertEntry();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCB.getItems().add("Family");
        typeCB.getItems().add("Sports");
        typeCB.getItems().add("Recreational");
    }
}
