package util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Customer {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String dateOfBirth;

    public Customer(TextField firstName, TextField lastName, TextField phone, TextField email, TextField address, DatePicker dateOfBirth) throws IllegalArgumentException {
        this(firstName.getText(), lastName.getText(), phone.getText(), email.getText(), address.getText(), dateOfBirth.getValue().toString());
    }

    public Customer(String firstName, String lastName, String phone, String email, String address, String dateOfBirth) throws IllegalArgumentException {
        boolean allValid = isValidArgument(firstName) && isValidArgument(lastName) && isValidArgument(phone) && isValidArgument(email) && isValidArgument(address) && isValidArgument(dateOfBirth);
        if (allValid) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.email = email;
            this.address = address;
            this.dateOfBirth = dateOfBirth;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isValidArgument(String argument) {
        return argument != null && !argument.isEmpty();
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

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
