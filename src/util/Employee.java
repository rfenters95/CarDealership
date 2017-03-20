package util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements Comparable<Employee> {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String dateOfBirth;
    private String jobTitle;

    public Employee(ResultSet resultSet) throws SQLException {
        this.firstName = resultSet.getString(2).trim();
        this.lastName = resultSet.getString(3).trim();
        this.phone = resultSet.getString(4).trim();
        this.email = resultSet.getString(5).trim();
        this.address = resultSet.getString(6).trim();
        this.city = resultSet.getString(7).trim();
        this.dateOfBirth = resultSet.getString(8).toString().trim();
        this.jobTitle = resultSet.getString(9).trim();
    }

    public Employee(TextField firstName, TextField lastName, TextField phone, TextField email, TextField address, TextField city, DatePicker dateOfBirth, ComboBox<String> jobTitle) {
        this(firstName.getText(), lastName.getText(), phone.getText(), email.getText(), address.getText(), city.getText(), dateOfBirth.getValue().toString(), jobTitle.getSelectionModel().getSelectedItem());
    }

    public Employee(String firstName, String lastName, String phone, String email, String address, String city, String dateOfBirth, String job) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.jobTitle = job;
    }

    @Override
    public int compareTo(Employee o) {
        int i = lastName.compareTo(o.lastName);
        if (i == 0) {
            return firstName.compareTo(o.firstName);
        }
        return i;
    }

    public String getRow() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInsertSQL() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s", DataHandler.getWrappedValue(firstName), DataHandler.getWrappedValue(lastName), DataHandler.getWrappedValue(phone), DataHandler.getWrappedValue(email), DataHandler.getWrappedValue(address), DataHandler.getWrappedValue(city), DataHandler.getWrappedValue(dateOfBirth), DataHandler.getWrappedValue(jobTitle));
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
