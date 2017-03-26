package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.DataHandler;
import util.Init;
import util.Session;
import util.Vehicle;

import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.TreeSet;

public class SearchVehicleTabController implements Init {

    private AlphaController alphaController;
    private ResultSet resultSet;

    @FXML private ComboBox<String> makeCB;
    @FXML private ComboBox<String> modelCB;
    @FXML private ComboBox<String> yearCB;
    @FXML private ComboBox<String> colorCB;
    @FXML private ComboBox<String> typeCB;
    @FXML private ComboBox<String> priceCB;
    @FXML private ComboBox<String> usedCB;
    @FXML private Button viewDetailsButton;

    @FXML private ListView<Vehicle> listView;
    @FXML private TitledPane tPane;
    @FXML private TitledPane vehicleResultsTP;

    private void clearResults() {
        listView.getItems().clear();
        makeCB.getItems().clear();
        modelCB.getItems().clear();
        yearCB.getItems().clear();
        colorCB.getItems().clear();
        typeCB.getItems().clear();
    }

    void displayResultSet() {

        tPane.setText(String.format(
                "Search Inventory - {Make = %s, Model = %s, Year = %s, " +
                        "Color = %s, Type = %s, Price = %s, Used = %s}",
                makeCB.getSelectionModel().getSelectedItem(),
                modelCB.getSelectionModel().getSelectedItem(),
                yearCB.getSelectionModel().getSelectedItem(),
                colorCB.getSelectionModel().getSelectedItem(),
                typeCB.getSelectionModel().getSelectedItem(),
                priceCB.getSelectionModel().getSelectedItem(),
                usedCB.getSelectionModel().getSelectedItem()
        ));

        try {

            boolean hasResults = resultSet.next();
            if (hasResults) {

                // Clear old results
                clearResults();

                TreeSet<String> makeCBItems = new TreeSet<>();
                TreeSet<String> modelCBItems = new TreeSet<>();
                TreeSet<String> yearCBItems = new TreeSet<>();
                TreeSet<String> colorCBItems = new TreeSet<>();
                TreeSet<String> typeCBItems = new TreeSet<>();

                int numberOfResults = 0;
                while (hasResults) {

                    Vehicle vehicle = new Vehicle(resultSet);

                    if (vehicle.getInStock().equals("Yes")) {
                        listView.getItems().add(vehicle);
                        makeCBItems.add(vehicle.getMake());
                        modelCBItems.add(vehicle.getModel());
                        yearCBItems.add(vehicle.getYear());
                        colorCBItems.add(vehicle.getColor());
                        typeCBItems.add(vehicle.getType());
                        numberOfResults++;
                    }

                    hasResults = resultSet.next();

                }

                vehicleResultsTP.setText(String.format("Results - %d", numberOfResults));
                Collections.sort(listView.getItems());
                makeCB.getItems().addAll(makeCBItems);
                modelCB.getItems().addAll(modelCBItems);
                yearCB.getItems().addAll(yearCBItems);
                colorCB.getItems().addAll(colorCBItems);
                typeCB.getItems().addAll(typeCBItems);

            } else {

                clearResults();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @FXML public void showAll(ActionEvent event) {

        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM VEHICLES");
            resultSet = preparedStatement.executeQuery();
            displayResultSet();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML public void select(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Session.selectedVehicle = listView.getSelectionModel().getSelectedItem();
            viewDetailsButton.setDisable(false);
        }
    }

    @FXML public void viewDetails(ActionEvent event) throws IOException {

        if (Session.selectedVehicle != null) {

            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader fxmlLoader = new FXMLLoader();
            newStage.setTitle("View Vehicle");

            fxmlLoader.setLocation(getClass().getResource("../views/VehicleDetails.fxml"));
            Parent newResource = fxmlLoader.load();
            Scene newScene = new Scene(newResource);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.showAndWait();

        } else {

            System.out.println("Error: No customer selected!");

        }
    }
    @FXML public void search(ActionEvent event) {

        // auto collapse search box for better viewing
        tPane.setExpanded(false);

        try {

            String sql = "SELECT * FROM `VEHICLES` WHERE";
            final String make = (makeCB.getSelectionModel().getSelectedIndex() == 0) ? null : makeCB.getSelectionModel().getSelectedItem();
            final String model = (modelCB.getSelectionModel().getSelectedIndex() == 0) ? null : modelCB.getSelectionModel().getSelectedItem();
            final String year = (yearCB.getSelectionModel().getSelectedIndex() == 0) ? null : yearCB.getSelectionModel().getSelectedItem();
            final String color = (colorCB.getSelectionModel().getSelectedIndex() == 0) ? null : colorCB.getSelectionModel().getSelectedItem();
            final String type = (typeCB.getSelectionModel().getSelectedIndex() == 0) ? null : typeCB.getSelectionModel().getSelectedItem();
            final String price = (priceCB.getSelectionModel().getSelectedIndex() == 0) ? null : priceCB.getSelectionModel().getSelectedItem();
            final String used = (usedCB.getSelectionModel().getSelectedIndex() == 0) ? null : usedCB.getSelectionModel().getSelectedItem();
            String[] attributes = {make, model, year, color, type, price, used};

            int j = 0; // flag indicates if all attributes are null
            boolean hasMultiple = false;
            for (int i = 0; i < attributes.length; i++) {
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
                            sql += " `MAKE`=" + DataHandler.getWrappedValue(make);
                            break;
                        case 1:
                            sql += " `MODEL`=" + DataHandler.getWrappedValue(model);
                            break;
                        case 2:
                            sql += " `YEAR`=" + DataHandler.getWrappedValue(year);
                            break;
                        case 3:
                            sql += " `COLOR`=" + DataHandler.getWrappedValue(color);
                            break;
                        case 4:
                            sql += " `TYPE`=" + DataHandler.getWrappedValue(type);
                            break;
                        case 5:
                            sql += " `PRICE`" + price;
                            break;
                        case 6:
                            sql += " `USED`=" + DataHandler.getWrappedValue(used);
                            break;
                        default:
                            break;
                    }
                }
            }

            // if all attributes are null do not send sql fragment
            if (j != 0) {

                Connection connection = DataHandler.getConnection();
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                displayResultSet();

            } else {

                Connection connection = DataHandler.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM VEHICLES");
                resultSet = preparedStatement.executeQuery();
                displayResultSet();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(AlphaController alphaController) {

        this.alphaController = alphaController;
        viewDetailsButton.setDisable(true);

        usedCB.getItems().add("Any");
        usedCB.getItems().add("No");
        usedCB.getItems().add("Yes");
        usedCB.getSelectionModel().select(0);

        listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
            @Override
            public ListCell<Vehicle> call(ListView<Vehicle> param) {
                return new ListCell<Vehicle>() {
                    @Override
                    protected void updateItem(Vehicle item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty && item != null) {
                            setText(item.toString());
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        makeCB.getItems().add("Any");
        makeCB.getSelectionModel().select(0);

        modelCB.getItems().add("Any");
        modelCB.getSelectionModel().select(0);

        yearCB.getItems().add("Any");
        yearCB.getSelectionModel().select(0);

        colorCB.getItems().add("Any");
        colorCB.getSelectionModel().select(0);

        typeCB.getItems().add("Any");
        typeCB.getSelectionModel().select(0);

        priceCB.getItems().add("Any");
        priceCB.getItems().add("<=20000");
        priceCB.getItems().add("<=40000");
        priceCB.getItems().add("<=60000");
        priceCB.getItems().add("<=80000");
        priceCB.getItems().add("<=100000");
        priceCB.getSelectionModel().select(0);

        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM VEHICLES");
            resultSet = statement.executeQuery();
            displayResultSet();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * Used after changes have been made to db to show all results
    * */
    void updateResultSet() {

        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM VEHICLES");
            resultSet = preparedStatement.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
