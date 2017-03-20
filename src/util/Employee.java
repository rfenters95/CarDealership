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
        this.firstName = resultSet.getString(2).replace(" ", "");
        this.lastName = resultSet.getString(3).replace(" ", "");
        this.phone = resultSet.getString(4).replace(" ", "");
        this.email = resultSet.getString(5).replace(" ", "");
        this.address = resultSet.getString(6).replace(" ", "");
        this.city = resultSet.getString(7).replace(" ", "");
        this.dateOfBirth = resultSet.getDate(8).toString().replace(" ", "");
        this.jobTitle = resultSet.getString(9).replace(" ", "");
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

    public boolean isAdmin() {
        return jobTitle.equals("Admin".replace(" ", ""));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDBInjection(int eid) {
        return String.format(" ('%d', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", eid, firstName, lastName, phone, email, address, city, dateOfBirth, jobTitle);
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
