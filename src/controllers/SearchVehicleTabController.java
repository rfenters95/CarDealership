package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import util.DataHandler;
import util.Init;
import util.Session;
import util.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

public class SearchVehicleTabController implements Init {

    private AlphaController alphaController;

    @FXML private ComboBox<String> makeCB;
    @FXML private ComboBox<String> modelCB;
    @FXML private ComboBox<String> yearCB;
    @FXML private ComboBox<String> colorCB;
    @FXML private ComboBox<String> typeCB;
    @FXML private ComboBox<String> priceCB;
    @FXML private Button viewDetailsButton;



    @FXML private ListView<Vehicle> listView;
    @FXML private TitledPane tPane;

    private void displayResultSet(ResultSet resultSet) throws SQLException {

        boolean hasResults = resultSet.next();

        TreeSet<Vehicle> vehicles = new TreeSet<>();

        if (hasResults) {

            listView.getItems().clear();

            do {

                Vehicle vehicle = new Vehicle(resultSet);
                vehicles.add(vehicle);

            } while (resultSet.next());

            listView.getItems().addAll(vehicles);

        } else {
            System.out.println("DB empty!");
        }

    }

    @FXML public void select(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Session.selectedVehicle = listView.getSelectionModel().getSelectedItem();
            viewDetailsButton.setDisable(false);
        }
    }
    @FXML public void viewDetails(ActionEvent event) {
        //launch invoice
    }
    @FXML public void search(ActionEvent event) {
        tPane.setExpanded(false);

        try {

            String sql;
            ResultSet resultSet;
            final String make = makeCB.getSelectionModel().getSelectedItem();
            final String model = modelCB.getSelectionModel().getSelectedItem();
            final String year = yearCB.getSelectionModel().getSelectedItem();
            final String color = colorCB.getSelectionModel().getSelectedItem();
            final String type = typeCB.getSelectionModel().getSelectedItem();

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            //TODO check for null too
            if (!make.isEmpty() && !model.isEmpty()) {

                sql = "SELECT * FROM VEHICLES WHERE MAKE=" + DataHandler.getWrappedValue(make)
                        + " AND MODEL=" + DataHandler.getWrappedValue(model);
                resultSet = statement.executeQuery(sql);
                displayResultSet(resultSet);

            } else {

                System.out.println("Empty search parameters! skip type & price for demo");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;
        viewDetailsButton.setDisable(true);

        listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
            @Override
            public ListCell<Vehicle> call(ListView<Vehicle> param) {
                return new ListCell<Vehicle>() {
                    @Override
                    protected void updateItem(Vehicle item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty && item != null) {
                            setText(item.getRow());
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        try {

            String sql;
            ResultSet resultSet;
            boolean hasResults;

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();

            sql = "SELECT * FROM VEHICLES";
            resultSet = statement.executeQuery(sql);
            hasResults = resultSet.next();

            TreeSet<Vehicle> vehicles = new TreeSet<>();
            TreeSet<String> makeCBItems = new TreeSet<>();
            TreeSet<String> modelCBItems = new TreeSet<>();
            TreeSet<String> yearCBItems = new TreeSet<>();
            TreeSet<String> colorCBItems = new TreeSet<>();
            TreeSet<String> typeCBItems = new TreeSet<>();
            TreeSet<String> priceCBItems = new TreeSet<>();

            if (hasResults) {

                do {

                    Vehicle vehicle = new Vehicle(resultSet);
                    vehicles.add(vehicle);
                    makeCBItems.add(vehicle.getMake());
                    modelCBItems.add(vehicle.getModel());
                    yearCBItems.add(vehicle.getYear());
                    colorCBItems.add(vehicle.getColor());
                    typeCBItems.add(vehicle.getType());

                } while (resultSet.next());

                listView.getItems().addAll(vehicles);
                makeCB.getItems().addAll(makeCBItems);
                modelCB.getItems().addAll(modelCBItems);
                yearCB.getItems().addAll(yearCBItems);
                colorCB.getItems().addAll(colorCBItems);
                typeCB.getItems().addAll(typeCBItems);

            } else {
                System.out.println("DB empty!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
