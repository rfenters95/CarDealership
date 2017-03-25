package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import util.Session;
import util.Vehicle;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class VehicleDetailsController implements Initializable {

    @FXML private TextField makeTF;
    @FXML private TextField modelTF;
    @FXML private TextField yearTF;
    @FXML private TextField colorTF;
    @FXML private TextField typeTF;
    @FXML private TextField priceTF;
    //@FXML private Label inStockLabel;

    /*
    @FXML public void view(ActionEvent event) {
        
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

        double dPrice = Double.parseDouble(vehicle.getPrice());
        DecimalFormat dPriceFormatter = new DecimalFormat("#,###.00");
        priceTF.setText(String.format("$%s", dPriceFormatter.format(dPrice)));
        priceTF.setDisable(true);

        /*
        try {

            String sql = "SELECT COUNT(`MAKE`) FROM `VEHICLES` " +
                    "WHERE `MAKE` = ? AND `MODEL` = ? " +
                    "AND `YEAR` = ? AND `COLOR` = ?";

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getYear());
            preparedStatement.setString(4, vehicle.getColor());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                inStockLabel.setText(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }
}
