package util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements Comparable<Customer> {

    private String ID;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String dateOfBirth;

    public Customer(ResultSet resultSet) throws SQLException {
        this.ID = resultSet.getString(1).trim();
        this.firstName = resultSet.getString(2).trim();
        this.lastName = resultSet.getString(3).trim();
        this.phone = resultSet.getString(4).trim();
        this.email = resultSet.getString(5).trim();
        this.address = resultSet.getString(6).trim();
        this.city = resultSet.getString(7).trim();
        this.dateOfBirth = resultSet.getDate(8).toString().trim();
    }

    public Customer(TextField firstName, TextField lastName, TextField phone, TextField email, TextField address, TextField city, DatePicker dateOfBirth) throws IllegalArgumentException {
        this(firstName.getText(), lastName.getText(), phone.getText(), email.getText(), address.getText(), city.getText(), dateOfBirth.getValue().toString());
    }

    public Customer(String firstName, String lastName, String phone, String email, String address, String city, String dateOfBirth) throws IllegalArgumentException {
        boolean allValid = isValidArgument(firstName) && isValidArgument(lastName) && isValidArgument(phone) && isValidArgument(email) && isValidArgument(address) && isValidArgument(city) && isValidArgument(dateOfBirth);
        if (allValid) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.email = email;
            this.address = address;
            this.city = city;
            this.dateOfBirth = dateOfBirth;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void insertEntry(Customer customer) throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO `CUSTOMERS` " +
                        "(`ID`, `FIRST_NAME`, `LAST_NAME`, `PHONE`, `EMAIL`, `ADDRESS`, `CITY`, `DATE_OF_BIRTH`) VALUES " +
                        "(NULL, ?, ?, ?, ?, ?, ?, ?);");
        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getPhone());
        preparedStatement.setString(4, customer.getEmail());
        preparedStatement.setString(5, customer.getAddress());
        preparedStatement.setString(6, customer.getCity());
        preparedStatement.setString(7, customer.getDateOfBirth());
        preparedStatement.executeUpdate();
    }

    public static void updateEntry(Customer customer) throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `CUSTOMERS` SET " +
                "`FIRST_NAME` = ?, " +
                "`LAST_NAME` = ?, " +
                "`PHONE` = ?, " +
                "`EMAIL` = ?, " +
                "`ADDRESS` = ?, " +
                "`CITY` = ?, " +
                "`DATE_OF_BIRTH` = ? WHERE " +
                "`ID` = ?");
        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getPhone());
        preparedStatement.setString(4, customer.getEmail());
        preparedStatement.setString(5, customer.getAddress());
        preparedStatement.setString(6, customer.getCity());
        preparedStatement.setString(7, customer.getDateOfBirth());
        preparedStatement.setString(8, customer.getID());
        preparedStatement.executeUpdate();
    }

    @Override
    public int compareTo(Customer o) {
        int i = lastName.compareTo(o.lastName);
        if (i == 0) {
            return firstName.compareTo(o.firstName);
        }
        return i;
    }

    private boolean isValidArgument(String argument) {
        return argument != null && !argument.isEmpty();
    }

    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
