package com.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.connections.DataValues;
import com.misc.Randomizer;

public class CashFlow {
	
//	private long commodities_amt;
//	private long equities_amt;
//	private long fixedincome_amt;
	private double yearly_expense;
	private double yearly_income;
	private double income_growth;
	private double[] market_returns = new double[4];    //Schema : Fixed income, Equities, Commodities, Inflation rate 
	private int current_year = 2018;						//Stores the goal completion year for longest goal
	private int last_year;	
	private List<Goals> all_goals=new ArrayList<Goals>();
	
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
		double yearly_expense_local;
		double yearly_income_local;
		
		String get_current_assets = "SELECT ASSET_AMT FROM CLIENT_ASSET WHERE USERNAME = ?";
		String get_client_financialInfo = "SELECT MONTHLY_INCOME, MONTHLY_EXPENSE, INC_GROWTH_RATE FROM FINANCIAL_INFO WHERE USERNAME = ?";
		String get_returnRates = "SELECT RATE_RETURN FROM MARKET_DATA";
		String get_goalTime = "SELECT  GOAL_TIME FROM CLIENT_GOAL WHERE USERNAME = ?";
		String insert_Cashflow = "INSERT INTO EXISTING_CASHFLOW VALUES (?,?,?,?,?,?,?,?)";
//		String get_networth = "SELECT CLIENT_NETWORTH FROM RISK_PROFILE WHERE USERNAME = '?' ";
		
		
		ResultSet current_assets = DataValues.fetchData(get_current_assets,userName);		//Get values from DB
		ResultSet client_info = DataValues.fetchData(get_client_financialInfo,userName);	//Get client's financial data
		ResultSet return_rates = DataValues.fetchData(get_returnRates);						//Get market return rates
		ResultSet goalTimes = DataValues.fetchData(get_goalTime,userName);							//Get client goals
		
		int max_year = 0;
		last_year = current_year + max_year;
		try {												//Get last goal's completion year from DB
			
			while(goalTimes.next())
			{	
				if(goalTimes.getInt(1)>max_year)
				{
					max_year = goalTimes.getInt(1);
					

					last_year = current_year + max_year;
				}
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count=0;
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
		
		while(current_year <= last_year)
		{
			double fixedIncome_returns=market_returns[0]+Randomizer.getRandomValue(-1,1)/100;
			double equity_returns=market_returns[1]+Randomizer.getRandomValue(-15,25)/100;
			double commodities_returns=market_returns[2]+Randomizer.getRandomValue(-15,20)/100;
			double inflation_rate=market_returns[3]+Randomizer.getRandomValue(-2,1.5)/100;
			
			fixedIncome_flow = asset_values[0]*fixedIncome_returns;
			asset_values[0] += fixedIncome_flow;
			equity_flow = asset_values[1]*equity_returns;
			asset_values[1] += equity_flow;
			commodities_flow = asset_values[2]*commodities_returns;
			asset_values[2] += commodities_flow;
			
			yearly_income_local += yearly_income_local*income_growth;
			yearly_expense_local += yearly_expense_local*inflation_rate;
			
			total_cash_inflow = yearly_income_local + fixedIncome_flow + equity_flow + commodities_flow;
			total_net_cashFlow = total_cash_inflow - yearly_expense_local;
			DataValues.addData(insert_Cashflow, userName, current_year, fixedIncome_flow, equity_flow, commodities_flow, total_cash_inflow, yearly_expense_local, total_net_cashFlow);
			
			current_year += 1;
		}	
	}

		public void alloc_cash()
		{
			double networth =0;
			double equities_value,fixedincome_value,commodities_value;
//			List<Goals> all_goals=new ArrayList<Goals>();
			
			String query_networth = "SELECT CLIENT_NETWORTH FROM RISK_PROFILE WHERE USERNAME = ?";
			String query_goals = "SELECT GOAL_ID,AMT_OUT,GOAL_TIME FROM CLIENT_GOAL WHERE USERNAME = ? order by goal_time";
			String query_add = "INSERT INTO PORTFOLIO_CASHFLOW VALUES (?,?,?,?,?,?,?,?)";
			String query_goal_status = "INSERT INTO GOAL_STATUS VALUES (?,?,?,?)";
			
			//fetch data: networth
			try 
			{
				ResultSet rs = DataValues.fetchData(query_networth,userName);
				while(rs.next())
					networth=rs.getInt(1);
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//get all goals
			try 
			{
				ResultSet rs = DataValues.fetchData(query_goals,userName);
				while(rs.next())
				{
					all_goals.add(new Goals(rs.getInt(3),rs.getInt(1),rs.getInt(2)));
				}
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//fetch data: allocated asset
			Allocation allocateObj = new Allocation();
			int[] asset_alloc=allocateObj.allocate();
			
			//calculate cash distribution
			fixedincome_value=(asset_alloc[0]*networth)/100;
			equities_value=(asset_alloc[1]*networth)/100;
			commodities_value=(asset_alloc[2]*networth)/100;
			
			double equities_returns,fixed_income_returns,commodities_returns;
			double equities,fixed_income,commodities;
			double portfolio_value,cash_in,cash_out;
			double inc_income =0,inc_expense =0;
			int access_year=2018;
			int goal_year,goal_id;
			double goal_amt;
			Goals current_goal;
			inc_income=yearly_income;
			inc_expense = yearly_expense;
			portfolio_value = networth;
			
			Iterator itr= all_goals.iterator();
		
			current_goal=(Goals)itr.next();
			goal_year = current_goal.getYear();
			System.out.println("Goal year: "+goal_year);
			goal_amt = current_goal.getAmount();
			goal_id = current_goal.getId();
			
			do
			{
				//calculate returns
				fixed_income_returns=(market_returns[0]+Randomizer.getRandomValue(-1,1))/100;
				equities_returns=(market_returns[1]+Randomizer.getRandomValue(-15,25))/100;
				commodities_returns=(market_returns[2]+Randomizer.getRandomValue(-15,20))/100;
				double inflation_rate=market_returns[3]+Randomizer.getRandomValue(-2,1.5)/100;
				
				//calculate cash flow
				fixed_income=fixedincome_value*fixed_income_returns;
				equities=equities_value*equities_returns;
				commodities=commodities_value*commodities_returns;
				
				inc_income = inc_income+(inc_income * income_growth);
				inc_expense = inc_expense+(inc_expense * inflation_rate);
				
				cash_in = fixed_income+equities+commodities+inc_income;
				cash_out = inc_expense;
				System.out.println("Cash out: "+cash_out);
				portfolio_value = portfolio_value + cash_in +cash_out;
				
				//add data values to table
				DataValues.addData(query_add,userName,access_year,fixed_income,equities,commodities,cash_in,cash_out,portfolio_value); 
			
				if(access_year == goal_year)
				{
					do {
					//goal is achieved
					if(portfolio_value>goal_amt)
					{
						portfolio_value = portfolio_value - goal_amt;
						DataValues.addData(query_goal_status,userName,goal_id,goal_year,1);
						fixedincome_value = fixedincome_value - (asset_alloc[0] * goal_amt)/100;
						equities_value = equities_value - (asset_alloc[1] * goal_amt)/100;
						commodities_value = commodities_value - (asset_alloc[2] * goal_amt)/100;
					}
					//goal is not achieved
					else
					{
						//update values
						DataValues.addData(query_goal_status,userName,goal_id,goal_year,0);
						fixedincome_value = fixed_income + fixedincome_value;
						equities_value = equities + equities_value;
						commodities_value = commodities + commodities_value;
					}
					
					//goal iterator
					if(itr.hasNext())
					{
						
						current_goal=(Goals)itr.next();
						goal_year = current_goal.getYear();
						goal_amt = current_goal.getAmount();
						goal_id = current_goal.getId();
						System.out.println("Goal year: "+goal_year);
					}
					else {
						break;
					}
					}while(access_year == goal_year);
				access_year++;
						
				}
				
				else
				{
					//update values
					fixedincome_value = fixed_income + fixedincome_value;
					equities_value = equities + equities_value;
					commodities_value = commodities + commodities_value;
					access_year++;
				}
				
		}while(access_year<=last_year);
	}
}

