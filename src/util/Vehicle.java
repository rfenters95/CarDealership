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
    private double price;
    private boolean isNew;

    public Vehicle(ResultSet resultSet) throws SQLException {
        this.make = resultSet.getString(1).replace(" ", "");
        this.model = resultSet.getString(2).replace(" ", "");
        this.year = resultSet.getString(3).replace(" ", "");
        this.color = resultSet.getString(4).replace(" ", "");
        this.type = resultSet.getString(5).replace(" ", "");
        this.price = resultSet.getDouble(6);
        this.isNew = resultSet.getBoolean(7);
    }

    public Vehicle(TextField make, TextField model, TextField year, TextField color, TextField type, TextField price, ComboBox<String> isNew) {
        this(make.getText(), model.getText(), year.getText(), color.getText(), type.getText(), Double.valueOf(price.getText()), (isNew.getSelectionModel().getSelectedItem()).equals("New"));
    }

    public Vehicle(String make, String model, String year, String color, String type, double price, boolean isNew) {
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

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
