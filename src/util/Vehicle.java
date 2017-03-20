package util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicle implements Comparable<Vehicle> {
    private String make;
    private String model;
    private String year;
    private String color;
    private String type;
    private String price;
    private String isNew;

    public Vehicle(ResultSet resultSet) throws SQLException {
        this.make = resultSet.getString(2).trim();
        this.model = resultSet.getString(3).trim();
        this.year = resultSet.getString(4).trim();
        this.color = resultSet.getString(5).trim();
        this.type = resultSet.getString(6).trim();
        this.price = resultSet.getString(7).trim();
        this.isNew = resultSet.getString(8).trim();
    }

    public Vehicle(TextField make, TextField model, TextField year, TextField color, TextField type, TextField price, ComboBox<String> isNew) {
        this(make.getText(), model.getText(), year.getText(), color.getText(), type.getText(), price.getText(), isNew.getSelectionModel().getSelectedItem());
    }

    public Vehicle(String make, String model, String year, String color, String type, String price, String isNew) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.price = price;
        this.isNew = isNew;
    }

    @Override
    public int compareTo(Vehicle o) {
        int i = make.compareTo(o.make);
        if (i == 0) {
            return model.compareTo(o.model);
        }
        return i;
    }

    public String getRow() {
        return make + " " + model;
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
}
