package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Formatter;
import util.Session;
import util.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VehicleDetailsController implements Initializable {

    @FXML private TextField makeTF;
    @FXML private TextField modelTF;
    @FXML private TextField yearTF;
    @FXML private TextField colorTF;
    @FXML private TextField typeTF;
    @FXML private TextField priceTF;

    private boolean inputEnabled = true;

    @FXML public void edit(ActionEvent event) throws IOException {

        inputEnabled = !inputEnabled;
        Button button = (Button) event.getSource();

        if (inputEnabled) {
            button.setText("Edit");
        } else {
            button.setText("View");
        }

        makeTF.setDisable(inputEnabled);
        modelTF.setDisable(inputEnabled);
        yearTF.setDisable(inputEnabled);
        colorTF.setDisable(inputEnabled);
        typeTF.setDisable(inputEnabled);
        priceTF.setDisable(inputEnabled);

    }

    @FXML public void save(ActionEvent event) {

        try {
            Session.getInstance().selectedVehicle.setMake(makeTF.getText());
            Session.getInstance().selectedVehicle.setModel(modelTF.getText());
            Session.getInstance().selectedVehicle.setYear(yearTF.getText());
            Session.getInstance().selectedVehicle.setColor(colorTF.getText());
            Session.getInstance().selectedVehicle.setType(typeTF.getText());
            Session.getInstance().selectedVehicle.setPrice(Formatter.USDtoString(priceTF.getText()));
            Vehicle.updateEntry(Session.getInstance().selectedVehicle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Session.getInstance().alphaController.getSearchVehicleTabController().updateResultSet();
        Session.getInstance().alphaController.getSearchVehicleTabController().displayResultSet();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Vehicle vehicle = Session.getInstance().selectedVehicle;

        makeTF.setText(vehicle.getMake());
        makeTF.setDisable(inputEnabled);

        modelTF.setText(vehicle.getModel());
        modelTF.setDisable(inputEnabled);

        yearTF.setText(vehicle.getYear());
        yearTF.setDisable(inputEnabled);

        colorTF.setText(vehicle.getColor());
        colorTF.setDisable(inputEnabled);

        typeTF.setText(vehicle.getType());
        typeTF.setDisable(inputEnabled);

        priceTF.setText(Formatter.USDFormatter(vehicle.getPrice()));
        priceTF.setDisable(inputEnabled);

    }
}
