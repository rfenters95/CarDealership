package controllers;

import components.NumberOnlyTextField;
import components.TextOnlyTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Session;
import util.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;

/*
*  AddTradeInVehicleController
*  Author: Reed Fenters
*
*  Enables user to input information on a new trade-in vehicle
*  then saves it in a database
* */
public class AddTradeInVehicleController implements Initializable {

    @FXML private TextOnlyTextField makeTF;
    @FXML private TextField modelTF;
    @FXML private NumberOnlyTextField yearTF;
    @FXML private TextOnlyTextField colorTF;
    @FXML private ComboBox<String> typeCB;
    @FXML private NumberOnlyTextField priceTF;

    // Save customer to the db
    @FXML public void save(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {

            // Empty field validation
            boolean isEmpty = !makeTF.getText().isEmpty();
            isEmpty = isEmpty && !modelTF.getText().isEmpty();
            isEmpty = isEmpty && !yearTF.getText().isEmpty();
            isEmpty = isEmpty && !colorTF.getText().isEmpty();
            isEmpty = isEmpty && !priceTF.getText().isEmpty();
            isEmpty = isEmpty && typeCB.getSelectionModel().getSelectedItem() != null;

            if (isEmpty) {

                // Create vehicle & save
                Vehicle vehicle = new Vehicle(makeTF, modelTF, yearTF, colorTF, typeCB, priceTF);
                Vehicle.insertEntry(vehicle);
                Session.getInstance().reloadVehicles();
                Session.getInstance().alert("Vehicle added!");
                stage.close();

            } else {
                Session.getInstance().alert("Error: empty fields!");
            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Init type options
        typeCB.getItems().add("Family");
        typeCB.getItems().add("Sports");
        typeCB.getItems().add("Recreational");

    }
}
