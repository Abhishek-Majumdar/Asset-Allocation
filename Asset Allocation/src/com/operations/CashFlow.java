package com.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.connections.DataValues;

public class CashFlow {
	
	private long commodities_amt;
	private long equities_amt;
	private long fixedincome_amt;
	private long yearly_income;
	private long yearly_expense;
	
	public void existing_cashflow()
	{
		int last_year = 0;
		String get_current_assets = "SELECT ASSET_ID, ASSET_AMT FROM CLIENT_ASSET WHERE USERNAME = ' '";
		String get_client_financialInfo = "SELECT MONTHLY_INCOME, MONTHLY_EXPENSE, INC_GROWTH_RATE FROM FINANCIAL_INFO WHERE USERNAME = ' '";
		String get_returnRates = "SELECT MARKET_ID, RATE_RETURN FROM MARKET_DATA";
		String get_goalTime = "SELECT  GOAL_TIME FROM CLIENT_GOAL";
		
		ResultSet goalTimes = DataValues.fetchData(get_goalTime);
		try {
			while(goalTimes.next())
			{
				last_year = goalTimes.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
