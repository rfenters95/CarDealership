package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.DataHandler;
import util.Vehicle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `VEHICLES` " +
                    "(`ID`, `MAKE`, `MODEL`, `YEAR`, `COLOR`, `TYPE`, `PRICE`, `USED`) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);");

            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getYear());
            preparedStatement.setString(4, vehicle.getColor());
            preparedStatement.setString(5, vehicle.getType());
            preparedStatement.setString(6, vehicle.getPrice());
            preparedStatement.setString(7, vehicle.getUsed());
            preparedStatement.executeUpdate();

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
