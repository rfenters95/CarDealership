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
        this(make.getText(), model.getText(), year.getText(), color.getText(), type.getSelectionModel().getSelectedItem(), price.getText(), "Yes");
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

    @Override
    public int compareTo(Vehicle o) {
        int i = make.compareTo(o.make);
        if (i == 0) {
            return model.compareTo(o.model);
        }
        return i;
    }

    public void insertEntry() throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `VEHICLES` " +
                "(`ID`, `MAKE`, `MODEL`, `YEAR`, `COLOR`, `TYPE`, `PRICE`, `USED`, `IN_STOCK`) VALUES " +
                "(NULL, ?, ?, ?, ?, ?, ?, ?, \"Yes\");");
        preparedStatement.setString(1, make);
        preparedStatement.setString(2, model);
        preparedStatement.setString(3, year);
        preparedStatement.setString(4, color);
        preparedStatement.setString(5, type);
        preparedStatement.setString(6, price);
        preparedStatement.setString(7, used);
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

    @Override
    public String toString() {
        return make + " " + model;
    }
}