package util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String dateOfBirth;

    public Customer(ResultSet resultSet) throws SQLException {
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

    @Override
    public int compareTo(Customer o) {
        int i = lastName.compareTo(o.lastName);
        if (i == 0) {
            return firstName.compareTo(o.firstName);
        }
        return i;
    }

    public String getRow() {
        return firstName + " " + lastName;
    }

    private boolean isValidArgument(String argument) {
        return argument != null && !argument.isEmpty();
    }

    public String getInsertSQL() {
        return String.format("%s, %s, %s, %s, %s, %s, %s", DataHandler.getWrappedValue(firstName), DataHandler.getWrappedValue(lastName), DataHandler.getWrappedValue(phone), DataHandler.getWrappedValue(email), DataHandler.getWrappedValue(address), DataHandler.getWrappedValue(city), DataHandler.getWrappedValue(dateOfBirth));
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

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
