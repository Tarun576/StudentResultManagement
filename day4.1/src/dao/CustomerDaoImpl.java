package dao;

import java.sql.*;
import pojos.Customer;
import static utils.DBUtils.getDBConnection;

public class CustomerDaoImpl implements ICustomerDao {
	private Connection cn;
	private PreparedStatement pst1;

	// def constr
	public CustomerDaoImpl() throws Exception {
		// get cn
		cn = getDBConnection();
		pst1 = cn.prepareStatement("select * from my_customers where email=? and password=?");
		System.out.println("customer dao created...");
	}

	@Override
	public Customer authenticateCustomer(String email, String pwd) throws SQLException {
		System.out.println("dao : auth customer");
		// set IN params
		pst1.setString(1, email);
		pst1.setString(2, pwd);
		// exec : exec query : select
		try (ResultSet rst = pst1.executeQuery()) {
			if (rst.next())
				return new Customer(rst.getInt(1), rst.getDouble(2), 
						email, rst.getString(4), pwd, rst.getDate(6),
						rst.getString(7));
		}
		return null;
	}
	public void cleanUp() throws SQLException
	{
		if(pst1 != null)
			pst1.close();
		if(cn != null)
			cn.close();
		System.out.println("customer dao cleaned up....");
	}

}
