package util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Employee {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String dateOfBirth;
    private boolean isAdmin;

    public Employee(TextField firstName, TextField lastName, TextField phone, TextField email, TextField address, DatePicker dateOfBirth, boolean isAdmin) {
        this(firstName.getText(), lastName.getText(), phone.getText(), email.getText(), address.getText(), dateOfBirth.getValue().toString(), isAdmin);
    }

    public Employee(String firstName, String lastName, String phone, String email, String address, String dateOfBirth, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isAdmin = isAdmin;
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
