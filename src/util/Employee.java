package util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements Comparable<Employee> {

    private String ID;
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

    private boolean isAdmin; // derived from jobTitle

    public Employee(ResultSet resultSet) throws SQLException {
        this.ID = resultSet.getString(1).trim();
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
        } else {
            // Assumes that all positions other than sales have admin access
            this.isAdmin = true;
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

    public static void insertEntry(Employee employee) throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO `EMPLOYEES` " +
                        "(`ID`, `FIRST_NAME`, `LAST_NAME`, `PHONE`, `EMAIL`, `ADDRESS`, `CITY`, `DATE_OF_BIRTH`) VALUES " +
                        "(NULL, ?, ?, ?, ?, ?, ?, ?);");
        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getPhone());
        preparedStatement.setString(4, employee.getEmail());
        preparedStatement.setString(5, employee.getAddress());
        preparedStatement.setString(6, employee.getCity());
        preparedStatement.setString(7, employee.getDateOfBirth());
        preparedStatement.executeUpdate();

        // Add USERS entry
        preparedStatement = connection.prepareStatement("INSERT INTO `USERS` " +
                "(`ID`, `USERNAME`, `PASSWORD`) VALUES " +
                "(NULL, ?, ?);");

        String username = employee.getEmail().substring(0, employee.getEmail().indexOf('@'));
        String password = String.valueOf(employee.getFirstName().charAt(0)) + String.valueOf(employee.getLastName().charAt(0));

        username = username.toLowerCase();
        password = password.toLowerCase();

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
    }

    public static void updateEntry(Employee employee) throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `EMPLOYEES` SET " +
                "`FIRST_NAME` = ?, " +
                "`LAST_NAME` = ?, " +
                "`PHONE` = ?, " +
                "`EMAIL` = ?, " +
                "`ADDRESS` = ?, " +
                "`CITY` = ?, " +
                "`DATE_OF_BIRTH` = ?," +
                "`JOB` = ?," +
                "`SALARY` = ?," +
                "`WORK_STATUS` = ?," +
                "`TOTAL_SALES` = ?" +
                " WHERE `ID` = ?");
        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getPhone());
        preparedStatement.setString(4, employee.getEmail());
        preparedStatement.setString(5, employee.getAddress());
        preparedStatement.setString(6, employee.getCity());
        preparedStatement.setString(7, employee.getDateOfBirth());
        preparedStatement.setString(8, employee.getJobTitle());
        preparedStatement.setString(9, employee.getSalary());
        preparedStatement.setString(10, employee.getWorkStatus());
        preparedStatement.setString(11, employee.getTotalSales());
        preparedStatement.setString(12, employee.getID());
        preparedStatement.executeUpdate();

        // Add USERS entry
        preparedStatement = connection.prepareStatement("UPDATE `USERS` SET `USERNAME` = ? `PASSWORD` = ? WHERE `ID` = ?");

        String username = employee.getEmail().substring(0, employee.getEmail().indexOf('@'));
        String password = String.valueOf(employee.getFirstName().charAt(0)) + String.valueOf(employee.getLastName().charAt(0));

        username = username.toLowerCase();
        password = password.toLowerCase();

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, employee.getID());
        preparedStatement.executeUpdate();
    }

    @Override
    public int compareTo(Employee o) {
        int i = jobTitle.compareTo(o.jobTitle);
        if (i == 0) {
            int j = lastName.compareTo(o.lastName);
            if (j == 0) {
                return firstName.compareTo(o.firstName);
            }
            return j;
        }
        return i;
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

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
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
        return String.format("[%s] %s, %s", jobTitle, lastName, firstName);
    }
}
