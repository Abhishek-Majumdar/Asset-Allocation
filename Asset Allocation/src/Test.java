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

	public static void main(String[] args) {

		CashFlow c1 = new CashFlow();
		//PlotGraph p1 = new PlotGraph();
		c1.setuser();
		c1.existing_cashflow();
		c1.alloc_cash();
		//ResultSet current_assets = DataValues.fetchData(get_current_assets,userName);		//Get values from DB
	//	double []market_returns = {0,0,0,0};
	//	p1.plotGraph();
		
//		System.out.println(Randomizer.getRandomValue(1, 100));
		
	}
	
}
