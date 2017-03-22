package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.DataHandler;
import util.Init;
import util.Vehicle;

import java.sql.Connection;
import java.sql.Statement;

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

            String sql;

            Vehicle vehicle = new Vehicle(makeTF, modelTF, yearTF, colorTF, typeCB, priceTF, usedCB);
            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = "INSERT INTO `VEHICLES` (`MAKE`, `MODEL`, `YEAR`, `COLOR`, `TYPE`, `PRICE`, `USED`) VALUES (" + vehicle.getInsertSQL() + ");";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            //TODO fix exception replicate with empty fields
            System.out.println("Empty fields!");
        }

        try {
            alphaController.getSearchVehicleTabController().updateResultSet();
            alphaController.getSearchVehicleTabController().displayResultSet();
        } catch (Exception e) {
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
