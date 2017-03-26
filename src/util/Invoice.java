package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Invoice {

    private String customerID;
    private String employeeID;
    private String vehicleID;
    private String date;
    private String paymentMethod;
    private String tradeInValue;
    private String warrantyValue;

    public Invoice(String customerID, String employeeID, String vehicleID, String date, String paymentMethod, String tradeInValue, String warrantyValue) {
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.vehicleID = vehicleID;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.tradeInValue = tradeInValue;
        this.warrantyValue = warrantyValue;
    }

    public Invoice(ResultSet resultSet) throws SQLException {
        this.customerID = resultSet.getString(1).trim();
        this.employeeID = resultSet.getString(2).trim();
        this.vehicleID = resultSet.getString(3).trim();
        this.date = resultSet.getString(4).trim();
        this.paymentMethod = resultSet.getString(5).trim();
        this.tradeInValue = resultSet.getString(6).trim();
        this.warrantyValue = resultSet.getString(7).trim();
    }

    public static void saveInvoice(Invoice invoice) throws Exception {
        Connection connection = DataHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `INVOICES` (`CUSTOMER_ID`, `EMPLOYEE_ID`, `VEHICLE_ID`, `DATE`, `PAYMENT_METHOD`, `TRADE_IN_VALUE`, `WARRANTY_VALUE`) VALUES (?, ?, ?, ?, ?, ?, ?);");
        preparedStatement.setString(1, invoice.customerID);
        preparedStatement.setString(2, invoice.employeeID);
        preparedStatement.setString(3, invoice.vehicleID);
        preparedStatement.setString(4, invoice.date);
        preparedStatement.setString(5, invoice.paymentMethod);
        preparedStatement.setString(6, invoice.tradeInValue);
        preparedStatement.setString(7, invoice.warrantyValue);
        preparedStatement.executeUpdate();
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTradeInValue() {
        return tradeInValue;
    }

    public String getWarrantyValue() {
        return warrantyValue;
    }

    @Override
    public String toString() {
        return date;
    }
}
