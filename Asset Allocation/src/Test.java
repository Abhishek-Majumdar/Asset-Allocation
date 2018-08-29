import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.connections.DataValues;
import com.connections.MyConnection;
import com.graph.PlotGraph;
import com.misc.Randomizer;
import com.operations.CashFlow;

public class Test {

	public static void main(String[] args) throws SQLException {

		while(true) {
		while(true) {
			
			
			MyConnection con = new MyConnection();
			Connection conn = con.getMyConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from user_session where user_status=1");
			if(rs.next()) {
				CashFlow c1 = new CashFlow();
				c1.setuser();
				c1.existing_cashflow();
				c1.alloc_cash();
				break;
			}
			
		}
		while(true) {
			MyConnection con = new MyConnection();
			Connection conn = con.getMyConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from user_session where user_status=1");
			if(!rs.next()) {
				break;
			}	
			
			
		}}
		
		
	
		
	}
	
}
