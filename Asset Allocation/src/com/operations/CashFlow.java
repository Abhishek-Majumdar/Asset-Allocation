package com.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.connections.DataValues;

public class CashFlow {
	
//	private long commodities_amt;
//	private long equities_amt;
//	private long fixedincome_amt;
//	private long yearly_income;
//	private long yearly_expense;
	private int monthly_expense;
	private int monthly_income;
	private int income_growth;
	private float market_returns[] = {0,0,0,0};    //Schema : Fixed income, Equities, Commodities, Inflation rate 
	private int last_year = 2018;
	
	private String userName;
	
	public void existing_cashflow()
	{
		int count = 0;
		
		int asset_values[] = {0,0,0}; 		//schema is Fixed, Equities, Commodities
		
		String get_current_assets = "SELECT ASSET_ID, ASSET_AMT FROM CLIENT_ASSET WHERE USERNAME = ' '";
		String get_client_financialInfo = "SELECT MONTHLY_INCOME, MONTHLY_EXPENSE, INC_GROWTH_RATE FROM FINANCIAL_INFO WHERE USERNAME = ' '";
		String get_returnRates = "SELECT MARKET_ID, RATE_RETURN FROM MARKET_DATA";
		String get_goalTime = "SELECT  GOAL_TIME FROM CLIENT_GOAL";
		String get_networth = "SELECT CLIENT_NETWORTH FROM RISK_PROFILE WHERE USERNAME = ' ' ";
		
		ResultSet current_assets = DataValues.fetchData(get_current_assets);		//Get values from DB
		ResultSet client_info = DataValues.fetchData(get_client_financialInfo);
		ResultSet return_rates = DataValues.fetchData(get_returnRates);
		ResultSet goalTimes = DataValues.fetchData(get_goalTime);
		
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
				monthly_income=client_info.getInt(1);
				monthly_expense=client_info.getInt(2);
				income_growth=client_info.getInt(3);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		count =0;
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
