package com.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.connections.DataValues;
import com.graph.PlotGraph;

public class Allocation {

	
	public int[] allocate()
	{
       	PlotGraph p1 = new PlotGraph();
       	List<Double> list = new ArrayList<>();
		list = p1.plotGraph();
		double return_total = list.get(0);
		double risk_appetite = list.get(1);
		
		double []return_component=new double[3];
		double []risk_component=new double[3];
		for(int i=0;i<3;i++)
		{
			return_component[i]=p1.returns.get(i);
			risk_component[i]=p1.risk.get(i);
		}
		
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
