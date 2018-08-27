package com.operations;

public class CashFlow {
	
	private long commodities_amt;
	private long equities_amt;
	private long fixedincome_amt;
	private long yearly_income;
	private long yearly_expense;
	
	public void existing_cashflow()
	{
		String get_current_assets = "SELECT ASSET_ID, ASSET_AMT FROM CLIENT_ASSET WHERE USERNAME = ' '";
		String get_client_financialInfo = "SELECT MONTHLY_INCOME, MONTHLY_EXPENSE, INC_GROWTH_RATE FROM FINANCIAL_INFO WHERE USERNAME = ' '";
		String get_returnRates = "SELECT MARKET_ID, RATE_RETURN FROM MARKET_DATA";
		String get_goalTime = "SELECT  ";
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
