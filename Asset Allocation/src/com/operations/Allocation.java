package com.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.connections.DataValues;
import com.graph.PlotGraph;

public class Allocation {

	double []return_component=new double[3];
	double []risk_component=new double[3];
	double return_total,risk_appetite;
	public Allocation() {
		// TODO Auto-generated constructor stub
		PlotGraph p1 = new PlotGraph();
       	List<Double> list = new ArrayList<>();
		list = p1.plotGraph();
		return_total = list.get(0);
		risk_appetite = list.get(1);
		
		
		for(int i=0;i<3;i++)
		{
			return_component[i]=p1.returns.get(i);
			risk_component[i]=p1.risk.get(i);
		}
	}
	
	public boolean check()
	{
		 ResultSet rs;
	        DataValues d1 = new DataValues();
	        int amount=0,principal=0;
	        double expected_rate;
	        rs = d1.fetchData("select AMT_OUT from CLIENT_GOAL");
	        try
	        {
	        while(rs.next())
	        {
	        	amount=rs.getInt(1);
	        }
	        }catch(SQLException e) 
	        {
	        	e.printStackTrace();
	        }
	        
	        rs = d1.fetchData("select CLIENT_NETWORTH from RISK_PROFILE");
	        try
	        {
	        while(rs.next())
	        {
	        	principal=rs.getInt(1);
	        }
	        }catch(SQLException e) 
	        {
	        	e.printStackTrace();
	        }
	        
	        expected_rate=((amount-principal)/principal)*100;
	        
	        if(expected_rate<return_total)
	        return true;
	        
	        return false;
	        		
	}
	
	public int[] allocate()
	{
		MatrixOperations mo=new MatrixOperations();
		double input[][]={ {1,1,1} , return_component , risk_component };
		double product[][]={{1},{return_total},{risk_appetite}};
		double result[][]=mo.multiplyMatrices((mo.invertMatrix(input)), product);
		
		int percent[]=new int[3];
		percent[0]=(int)(result[0][0]*100);
		percent[1]=(int)(result[1][0]*100);
		percent[2]=100-percent[0]-percent[1];
		//System.out.println(percent[0]+","+percent[1]+","+percent[2]);
		return percent;
	}

}
