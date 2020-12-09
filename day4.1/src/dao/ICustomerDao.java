package dao;

import java.sql.SQLException;

import pojos.Customer;

public interface ICustomerDao {
	Customer authenticateCustomer(String email, String pwd) throws SQLException;
}
