package com.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.connections.DataValues;
import com.misc.Randomizer;

public class CashFlow {
	
//	private long commodities_amt;
//	private long equities_amt;
//	private long fixedincome_amt;
//	private long yearly_income;
//	private long yearly_expense;
	private double yearly_expense;
	private double yearly_income;
	private double income_growth;
	private double market_returns[] = {0,0,0,0};    //Schema : Fixed income, Equities, Commodities, Inflation rate 
	private int last_year = 2018;					//Stores the goal completion year for longest goal
	
	private String userName;
	
	public void setuser()							//For session management
	{
		String query="SELECT USERNAME FROM USER_SESSION WHERE USER_STATUS=1";
		ResultSet rs = DataValues.fetchData(query);
		try 
		{
			rs.next();
			userName=rs.getString(1);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void existing_cashflow()					//Calculates cash-flow from existing assets
	{
		int count = 0;
		
		int asset_values[] = {0,0,0}; 		//schema is Fixed, Equities, Commodities
		int current_year = 2018;
		double yearly_expense_local;
		double yearly_income_local;
		
		String get_current_assets = "SELECT ASSET_ID, ASSET_AMT FROM CLIENT_ASSET WHERE USERNAME = '?'";
		String get_client_financialInfo = "SELECT MONTHLY_INCOME, MONTHLY_EXPENSE, INC_GROWTH_RATE FROM FINANCIAL_INFO WHERE USERNAME = '?'";
		String get_returnRates = "SELECT MARKET_ID, RATE_RETURN FROM MARKET_DATA";
		String get_goalTime = "SELECT  GOAL_TIME FROM CLIENT_GOAL";
		String insert_Cashflow = "INSERT INTO DATABASE VALUES '?','?','?','?','?','?','?','?','?'";
//		String get_networth = "SELECT CLIENT_NETWORTH FROM RISK_PROFILE WHERE USERNAME = '?' ";
		
		ResultSet current_assets = DataValues.fetchData(get_current_assets,userName);		//Get values from DB
		ResultSet client_info = DataValues.fetchData(get_client_financialInfo,userName);	//Get client's financial data
		ResultSet return_rates = DataValues.fetchData(get_returnRates);						//Get market return rates
		ResultSet goalTimes = DataValues.fetchData(get_goalTime);							//Get client goals
		
		try {												//Get last goal's completion year from DB
			while(goalTimes.next())
			{
				last_year = goalTimes.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {												//Get existing asset values from DB
			while(current_assets.next())
			{
				asset_values[count] = current_assets.getInt(1);
				count += 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		count=0;
		
		try {												//Get market ROI's and inflation rates from DB
			while(return_rates.next())
			{
				market_returns[count] = return_rates.getInt(1);
				count += 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 												//Get client's financial data from DB
		{
			while(client_info.next())
			{
				yearly_income=client_info.getInt(1)*12;
				yearly_expense=client_info.getInt(2)*12;
				income_growth=client_info.getInt(3)/100;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		count =0;

		double equity_flow = 0.0;
		double fixedIncome_flow = 0.0;
		double commodities_flow = 0.0;
		double total_cash_inflow = 0.0;
		double total_net_cashFlow = 0.0;
		
		yearly_income_local = yearly_income;
		yearly_expense_local = yearly_expense;
		
		while(current_year < last_year)
		{
			double fixedIncome_returns=market_returns[0]*Randomizer.getRandomValue(-1,2)/100;
			double equity_returns=market_returns[1]*Randomizer.getRandomValue(-10,10)/100;
			double commodities_returns=market_returns[2]*Randomizer.getRandomValue(-5,10)/100;
			
			fixedIncome_flow = asset_values[0]*fixedIncome_returns;
			asset_values[0] += fixedIncome_flow;
			equity_flow = asset_values[1]*equity_returns;
			asset_values[1] += equity_flow;
			commodities_flow = asset_values[2]*commodities_returns;
			asset_values[2] += commodities_flow;
			
			yearly_income_local += yearly_income*income_growth;
			yearly_expense_local += yearly_expense*market_returns[3];
			
			total_cash_inflow = yearly_income_local + fixedIncome_flow + equity_flow + commodities_flow;
			total_net_cashFlow = total_cash_inflow - yearly_expense_local;
			
			DataValues.addData(insert_Cashflow, userName, current_year, fixedIncome_flow, equity_flow, commodities_flow, total_cash_inflow, yearly_expense_local, total_net_cashFlow);
			
			current_year += 1;
		}	
	}
}
