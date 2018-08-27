package com.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.connections.DataValues;
import com.graph.PlotGraph;

public class Allocation {

	public int[] allocate(double [] return_component,double []risk_component)
	{
		double return_total; 
		double risk_appetite;
		
       	MatrixOperations mo=new MatrixOperations();
       	List<Double> list = new ArrayList<>();
		PlotGraph p1 = new PlotGraph();
		list = p1.plotGraph();
		return_total = list.get(0);
		risk_appetite = list.get(1);
		
		double input[][]={ {1,1,1} , return_component , risk_component };
		double product[][]={{1},{return_total},{risk_appetite}};
		double result[][]=mo.multiplyMatrices((mo.invertMatrix(input)), product);
		
		int percent[]=new int[3];
		percent[0]=(int)(result[0][0]*100);
		percent[1]=(int)(result[1][0]*100);
		percent[2]=100-percent[0]-percent[1];
		System.out.println(percent[0]+","+percent[1]+","+percent[2]);
		return percent;
	}

}
