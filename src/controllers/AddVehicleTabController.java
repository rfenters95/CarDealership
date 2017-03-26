package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.Init;
import util.Vehicle;

public class AddVehicleTabController implements Init {

    private AlphaController alphaController;

    @FXML private TextField makeTF;
    @FXML private TextField modelTF;
    @FXML private TextField yearTF;
    @FXML private TextField colorTF;
    @FXML private ComboBox<String> typeCB;
    @FXML private TextField priceTF;
    @FXML private ComboBox<String> usedCB;



    @FXML public void save(ActionEvent event) {

        try {

            Vehicle vehicle = new Vehicle(makeTF, modelTF, yearTF, colorTF, typeCB, priceTF, usedCB);
            vehicle.insertEntry();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            alphaController.getSearchVehicleTabController().updateResultSet();
            alphaController.getSearchVehicleTabController().displayResultSet();

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
