import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.connections.DataValues;
import com.connections.MyConnection;
import com.operations.CashFlow;

public class Test {

	public static void main(String[] args) {

		CashFlow c1 = new CashFlow();
		c1.setuser();
		c1.existing_cashflow();
		//ResultSet current_assets = DataValues.fetchData(get_current_assets,userName);		//Get values from DB
	//	double []market_returns = {0,0,0,0};
		
		
	}
	
}