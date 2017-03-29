package util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle implements Comparable<Vehicle> {

    private String ID;
    private String make;
    private String model;
    private String year;
    private String color;
    private String type;
    private String price;
    private String used;
    private String inStock;

    public Vehicle(ResultSet resultSet) throws SQLException {
        this.ID = resultSet.getString(1).trim();
        this.make = resultSet.getString(2).trim();
        this.model = resultSet.getString(3).trim();
        this.year = resultSet.getString(4).trim();
        this.color = resultSet.getString(5).trim();
        this.type = resultSet.getString(6).trim();
        this.price = resultSet.getString(7).trim();
        this.used = resultSet.getString(8).trim();
        this.inStock = resultSet.getString(9).trim();
    }

    public Vehicle(TextField make, TextField model, TextField year, TextField color, ComboBox<String> type, TextField price, ComboBox<String> used) {
        this(make.getText(), model.getText(), year.getText(), color.getText(), type.getSelectionModel().getSelectedItem(), price.getText(), used.getSelectionModel().getSelectedItem());
    }

    public Vehicle(TextField make, TextField model, TextField year, TextField color, ComboBox<String> type, TextField price) {
        this(
                Formatter.parseName(make.getText()),
                Formatter.parseName(model.getText()),
                year.getText(),
                Formatter.parseName(color.getText()),
                type.getSelectionModel().getSelectedItem(),
                Formatter.parseNumber(price.getText()),
                "Yes"
        );
    }

    public Vehicle(String make, String model, String year, String color, String type, String price, String used) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.price = price;
        this.used = used;
        this.inStock = "Yes";
    }

    public static void removeVehicle() throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `VEHICLES` SET `IN_STOCK` = \"No\" WHERE `ID` = ?");
        preparedStatement.setString(1, Session.getInstance().selectedVehicle.getID());
        preparedStatement.executeUpdate();
    }

    @Override
    public int compareTo(Vehicle o) {
        int i = make.compareTo(o.make);
        if (i == 0) {
            return model.compareTo(o.model);
        }
        return i;
    }

    public static void insertEntry(Vehicle vehicle) throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `VEHICLES` " +
                "(`ID`, `MAKE`, `MODEL`, `YEAR`, `COLOR`, `TYPE`, `PRICE`, `USED`, `IN_STOCK`) VALUES " +
                "(NULL, ?, ?, ?, ?, ?, ?, ?, ?);");
        preparedStatement.setString(1, vehicle.getMake());
        preparedStatement.setString(2, vehicle.getModel());
        preparedStatement.setString(3, vehicle.getYear());
        preparedStatement.setString(4, vehicle.getColor());
        preparedStatement.setString(5, vehicle.getType());
        preparedStatement.setString(6, vehicle.getPrice());
        preparedStatement.setString(7, vehicle.getUsed());
        preparedStatement.setString(8, vehicle.getInStock());
        preparedStatement.executeUpdate();
    }

    public static void updateEntry(Vehicle vehicle) throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `VEHICLES` SET " +
                "`MAKE` = ?, " +
                "`MODEL` = ?, " +
                "`YEAR` = ?, " +
                "`COLOR` = ?, " +
                "`TYPE` = ?, " +
                "`PRICE` = ?, " +
                "`USED` = ?, " +
                "`IN_STOCK` = ? WHERE " +
                "`ID` = ?");
        preparedStatement.setString(1, vehicle.getMake());
        preparedStatement.setString(2, vehicle.getModel());
        preparedStatement.setString(3, vehicle.getYear());
        preparedStatement.setString(4, vehicle.getColor());
        preparedStatement.setString(5, vehicle.getType());
        preparedStatement.setString(6, vehicle.getPrice());
        preparedStatement.setString(7, vehicle.getUsed());
        preparedStatement.setString(8, vehicle.getInStock());
        preparedStatement.setString(9, vehicle.getID());
        preparedStatement.executeUpdate();
    }

    public String getID() {
        return ID;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getUsed() {
        return used;
    }

    public String getInStock() {
        return inStock;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return make + " " + model;
    }
}