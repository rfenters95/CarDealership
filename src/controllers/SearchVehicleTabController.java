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

    @FXML private Label selectedLabel;

    public void displayResultSet() {

        String make = makeCB.getSelectionModel().getSelectedItem();
        String model = modelCB.getSelectionModel().getSelectedItem();
        String year = yearCB.getSelectionModel().getSelectedItem();
        String color = colorCB.getSelectionModel().getSelectedItem();
        String type = typeCB.getSelectionModel().getSelectedItem();
        String price = priceCB.getSelectionModel().getSelectedItem();
        String used = usedCB.getSelectionModel().getSelectedItem();

        setBoxes();

        tPane.setText(String.format(
                "Search Inventory - {Make = %s, Model = %s, Year = %s, " +
                        "Color = %s, Type = %s, Price = %s, Used = %s}",
                make, model, year, color, type, price, used
        ));

        try {

            boolean hasResults = resultSet.next();
            if (hasResults) {

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

                setResultTPTitle(String.format("Results - %d", numberOfResults));
                Collections.sort(listView.getItems());
                makeCB.getItems().addAll(makeCBItems);
                modelCB.getItems().addAll(modelCBItems);
                yearCB.getItems().addAll(yearCBItems);
                colorCB.getItems().addAll(colorCBItems);
                typeCB.getItems().addAll(typeCBItems);

            } else {
                setResultTPTitle(String.format("Results - %d", 0));
                Session.getInstance().alert("No matches!");
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @FXML public void showAll(ActionEvent event) {

        updateResultSet();
        displayResultSet();

    }

    @FXML public void select(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Session.getInstance().selectedVehicle = listView.getSelectionModel().getSelectedItem();
            viewDetailsButton.setDisable(false);
            selectedLabel.setText("Selected - " + Session.getInstance().selectedVehicle.toString());
        }
    }

    @FXML public void viewDetails(ActionEvent event) throws IOException {

        if (Session.getInstance().selectedVehicle != null) {

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
            Session.getInstance().alert("Error: No customer selected!");
        }

    }
    @FXML public void search(ActionEvent event) {

        // Auto collapse search box for better viewing
        tPane.setExpanded(false);

        try {

            String sql = "SELECT * FROM `VEHICLES` WHERE";
            String make = (makeCB.getSelectionModel().getSelectedItem().equals("Any")) ? null : makeCB.getSelectionModel().getSelectedItem();
            String model = (modelCB.getSelectionModel().getSelectedItem().equals("Any")) ? null : modelCB.getSelectionModel().getSelectedItem();
            String year = (yearCB.getSelectionModel().getSelectedItem().equals("Any")) ? null : yearCB.getSelectionModel().getSelectedItem();
            String color = (colorCB.getSelectionModel().getSelectedItem().equals("Any")) ? null : colorCB.getSelectionModel().getSelectedItem();
            String type = (typeCB.getSelectionModel().getSelectedItem().equals("Any")) ? null : typeCB.getSelectionModel().getSelectedItem();
            String price = (priceCB.getSelectionModel().getSelectedItem().equals("Any")) ? null : priceCB.getSelectionModel().getSelectedItem();
            String used = (usedCB.getSelectionModel().getSelectedItem().equals("Any")) ? null : usedCB.getSelectionModel().getSelectedItem();
            String[] attributes = {make, model, year, color, type, price, used};

            int j = 0; // Flag indicates if all attributes are null
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

            // If all attributes are null do not send sql fragment
            if (j != 0) {

                Connection connection = DataHandler.getConnection();
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
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

        selectedLabel.setText("Selected - None");

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

        setBoxes();

        updateResultSet();
        displayResultSet();

    }

    /*
    * Used after changes have been made to db to show all results
    * */
    public void updateResultSet() {

        try {

            Connection connection = DataHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM VEHICLES");
            resultSet = preparedStatement.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setBoxes() {

        listView.getItems().clear();

        makeCB.getItems().clear();
        makeCB.getItems().add("Any");
        makeCB.getSelectionModel().select(0);

        modelCB.getItems().clear();
        modelCB.getItems().add("Any");
        modelCB.getSelectionModel().select(0);

        yearCB.getItems().clear();
        yearCB.getItems().add("Any");
        yearCB.getSelectionModel().select(0);

        colorCB.getItems().clear();
        colorCB.getItems().add("Any");
        colorCB.getSelectionModel().select(0);

        typeCB.getItems().clear();
        typeCB.getItems().add("Any");
        typeCB.getSelectionModel().select(0);

        priceCB.getItems().clear();
        priceCB.getItems().add("Any");
        priceCB.getItems().add("<=20000");
        priceCB.getItems().add("<=40000");
        priceCB.getItems().add("<=60000");
        priceCB.getItems().add("<=80000");
        priceCB.getItems().add("<=100000");
        priceCB.getSelectionModel().select(0);

        usedCB.getItems().clear();
        usedCB.getItems().add("Any");
        usedCB.getItems().add("No");
        usedCB.getItems().add("Yes");
        usedCB.getSelectionModel().select(0);
    }

    private void setResultTPTitle(String title) {
        vehicleResultsTP.setText(title);
    }

}