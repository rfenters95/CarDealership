package util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Invoice {

    private String customerID;
    private String employeeID;
    private String vehicleID;
    private String date;

    public Invoice(ResultSet resultSet) throws SQLException {
        this.customerID = resultSet.getString(1).trim();
        this.employeeID = resultSet.getString(2).trim();
        this.vehicleID = resultSet.getString(3).trim();
        this.date = resultSet.getString(4).trim();
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date;
    }
}
