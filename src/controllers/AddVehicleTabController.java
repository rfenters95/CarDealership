package controllers;

import components.NumberOnlyTextField;
import components.TextOnlyTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.Init;
import util.Session;
import util.Vehicle;

public class AddVehicleTabController implements Init {

    private AlphaController alphaController;

    @FXML private TextOnlyTextField makeTF;
    @FXML private TextField modelTF;
    @FXML private NumberOnlyTextField yearTF;
    @FXML private TextOnlyTextField colorTF;
    @FXML private ComboBox<String> typeCB;
    @FXML private NumberOnlyTextField priceTF;
    @FXML private ComboBox<String> usedCB;

    @FXML public void save(ActionEvent event) {

        try {

            boolean isEmpty = !makeTF.getText().isEmpty();
            isEmpty = isEmpty && !modelTF.getText().isEmpty();
            isEmpty = isEmpty && !yearTF.getText().isEmpty();
            isEmpty = isEmpty && !colorTF.getText().isEmpty();
            isEmpty = isEmpty && !priceTF.getText().isEmpty();
            isEmpty = isEmpty && typeCB.getSelectionModel().getSelectedItem() != null;
            isEmpty = isEmpty && usedCB.getSelectionModel().getSelectedItem() != null;

            if (isEmpty) {

                Vehicle vehicle = new Vehicle(makeTF, modelTF, yearTF, colorTF, typeCB, priceTF, usedCB);
                Vehicle.insertEntry(vehicle);
                Session.getInstance().reloadVehicles();
                Session.getInstance().alert("Vehicle Added!");

            } else {
                Session.getInstance().alert("Error: Empty fields!");
            }

        } catch (Exception e) {
            Session.getInstance().alert("Error: Contact Admin!");
            e.printStackTrace();
        }

    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;

        usedCB.getItems().add("No");
        usedCB.getItems().add("Yes");

        typeCB.getItems().add("Family");
        typeCB.getItems().add("Sports");
        typeCB.getItems().add("Recreational");

    }
}
