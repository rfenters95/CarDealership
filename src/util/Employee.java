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
    private String salary;
    private String workStatus;
    private String totalSales;

    public Employee(ResultSet resultSet) throws SQLException {
        this.firstName = resultSet.getString(2).trim();
        this.lastName = resultSet.getString(3).trim();
        this.phone = resultSet.getString(4).trim();
        this.email = resultSet.getString(5).trim();
        this.address = resultSet.getString(6).trim();
        this.city = resultSet.getString(7).trim();
        this.dateOfBirth = resultSet.getString(8).trim();
        this.jobTitle = resultSet.getString(9).trim();
        this.salary = resultSet.getString(10).trim();
        this.workStatus = resultSet.getString(11).trim();
        if (this.jobTitle.equals("Sales")) {
            this.totalSales = resultSet.getString(12).trim();
        }
    }

    public Employee(TextField firstName, TextField lastName, TextField phone, TextField email, TextField address, TextField city, DatePicker dateOfBirth, ComboBox<String> jobTitle, TextField salary) {
        this.firstName = firstName.getText();
        this.lastName = lastName.getText();
        this.phone = phone.getText();
        this.email = email.getText();
        this.address = address.getText();
        this.city = city.getText();
        this.dateOfBirth = dateOfBirth.getValue().toString();
        this.jobTitle = jobTitle.getSelectionModel().getSelectedItem();
        this.salary = salary.getText();
        this.workStatus = "1";
        this.totalSales = "0";
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

    public String getInsertSQL() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                DataHandler.getWrappedValue(firstName),
                DataHandler.getWrappedValue(lastName),
                DataHandler.getWrappedValue(phone),
                DataHandler.getWrappedValue(email),
                DataHandler.getWrappedValue(address),
                DataHandler.getWrappedValue(city),
                DataHandler.getWrappedValue(dateOfBirth),
                DataHandler.getWrappedValue(jobTitle),
                DataHandler.getWrappedValue(salary),
                DataHandler.getWrappedValue(workStatus),
                DataHandler.getWrappedValue(totalSales));
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

    public String getJobTitle() {
        return jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public String getPercentCommission() {
        double tSales = Double.parseDouble(totalSales);
        if (tSales <= 100_000) {
            return String.valueOf(0.05);
        } else if (tSales > 100_000 && tSales >= 200_000) {
            return String.valueOf(0.07);
        } else {
            return String.valueOf(0.1);
        }
    }

    public String getCommission() {
        double tSales = Double.parseDouble(totalSales);
        double commission = Double.parseDouble(getPercentCommission());
        return String.valueOf(tSales * commission);
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }
}
