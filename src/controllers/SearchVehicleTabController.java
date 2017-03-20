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
    public ResultSet resultSet;

    @FXML private ComboBox<String> makeCB;
    @FXML private ComboBox<String> modelCB;
    @FXML private ComboBox<String> yearCB;
    @FXML private ComboBox<String> colorCB;
    @FXML private ComboBox<String> typeCB;
    @FXML private ComboBox<String> priceCB;
    @FXML private ComboBox<String> isNewCB;
    @FXML private Button viewDetailsButton;



    @FXML private ListView<Vehicle> listView;
    @FXML private TitledPane tPane;

    private void displayResultSet() throws SQLException {

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

            String sql = "SELECT * FROM VEHICLES WHERE";
            final String make = makeCB.getSelectionModel().getSelectedItem();
            final String model = modelCB.getSelectionModel().getSelectedItem();
            final String year = yearCB.getSelectionModel().getSelectedItem();
            final String color = colorCB.getSelectionModel().getSelectedItem();
            final String type = typeCB.getSelectionModel().getSelectedItem();
            final String price = priceCB.getSelectionModel().getSelectedItem();
            final String isNew = isNewCB.getSelectionModel().getSelectedItem();
            String[] attributes = {make, model, year, color, type, price, isNew};

            boolean hasMultiple = false;
            for (int i = 0, j = 0; i < attributes.length; i++) {
                if (attributes[i] != null) {

                    j++;
                    if (j == 2) {
                        hasMultiple = true;
                    }


                    if (hasMultiple) {
                        sql += " AND";
                    }


                    switch (i) {
                        case 0:
                            sql += " MAKE=" + DataHandler.getWrappedValue(make);
                            break;
                        case 1:
                            sql += " MODEL=" + DataHandler.getWrappedValue(model);
                            break;
                        case 2:
                            sql += " YEAR=" + DataHandler.getWrappedValue(year);
                            break;
                        case 3:
                            sql += " COLOR=" + DataHandler.getWrappedValue(color);
                            break;
                        case 4:
                            sql += " TYPE=" + DataHandler.getWrappedValue(type);
                            break;
                        case 5:
                            sql += " PRICE=" + DataHandler.getWrappedValue(price);
                            break;
                        case 6:
                            sql += " IS_NEW=" + DataHandler.getWrappedValue(isNew);
                            break;
                        default:
                            break;
                    }
                }
            }

            Connection connection = DataHandler.getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            displayResultSet();

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
