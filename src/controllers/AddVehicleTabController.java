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
    @FXML private TextField typeTF;
    @FXML private TextField priceTF;
    @FXML private ComboBox<String> conditionCB;



    @FXML public void save(ActionEvent event) {
        try {
            Vehicle vehicle = new Vehicle(makeTF, modelTF, yearTF, colorTF, typeTF, priceTF, conditionCB);
            System.out.println(vehicle.getModel());
        } catch (Exception e) {
            System.out.println("Empty fields");
        }
    }

    @Override
    public void init(AlphaController alphaController) {
        this.alphaController = alphaController;
        conditionCB.getItems().add("New");
        conditionCB.getItems().add("Used");
    }
}
