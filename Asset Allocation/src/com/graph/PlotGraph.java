package com.graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.connections.DataValues;



public class PlotGraph {
    public double plotGraph() {
        ArrayList<Double> risk = new ArrayList<Double>();
        ArrayList<Double> returns = new ArrayList<Double>();
        ResultSet rs;
        DataValues d1 = new DataValues();
        rs = d1.fetchData("select * from market_data");
        
        try {
			while(rs.next()) {
				risk.add(rs.getDouble(1));
				returns.add(rs.getDouble(2));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        keyPoints.add(4.89); Risk
//        keyPoints.add(19.41);
//        keyPoints.add(28.41);
//      
//        ArrayList<Double> keyPoints1 = new ArrayList<Double>();
//        keyPoints1.add(5.80);	Return
//        keyPoints1.add(11.74);
//        keyPoints1.add(7.29);
        
        WeightedObservedPoints obs = new WeightedObservedPoints();
        if(risk != null && risk.size() != 1) {
            int size = risk.size();
            //int sectionSize = (int) (1000 / (size - 1));
            for(int i = 0; i < size; i++) {
                    obs.add(risk.get(i), returns.get(i));
               
            }
        } 
        
//        obs.add(5,5);
//        obs.add(7, 10);
//        obs.add(10, 11);

        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);
        //fitter.withStartPoint(new double[] {keyPoints.get(0), 1});
        fitter.withStartPoint(new double[] {1,1});
        double[] coeff = fitter.fit(obs.toList());
        System.out.println(Arrays.toString(coeff));
        int risk_Score = 0;
         String username = null;
        rs = d1.fetchData("select * from user_session where user_status=1");
        try {
			while(rs.next()) {
				username = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ResultSet rs1 = d1.fetchData("select risk_Score from risk_profile where username = ?",username);
        try {
			while(rs1.next()) {
				try {
					risk_Score = rs1.getInt(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // coeff[0] -= risk_score;
        PolynomialFunction polynomial = new PolynomialFunction(coeff);
        double y =polynomial.value(risk_Score);
      
    //    laguerreSolver.s
     //   System.out.println("For x = 11, we found y = " + y);
        
        return y;
    }
}