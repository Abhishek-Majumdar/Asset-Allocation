package com.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataValues {
	
	public static ResultSet fetchData(String query,String param1) {
		
		Connection conn = MyConnection.getMyConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, param1);
			ResultSet rs = ps.executeQuery();
			
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	public static ResultSet fetchData(String query) {
		
		Connection conn = MyConnection.getMyConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void addData(String query) {
		
		Connection conn = MyConnection.getMyConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addData(String query,int year,double val1,double val2, double val3, double val4, double val5, double val6) {
		
		Connection conn = MyConnection.getMyConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, year);
			ps.setDouble(2, val1);
			ps.setDouble(3, val2);
			ps.setDouble(4, val3);
			ps.setDouble(5, val4);
			ps.setDouble(6, val5);
			ps.setDouble(7, val6);
			ps.executeUpdate(query);
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
public void addData(String query,int year,double val1,double val2, double val3, double val4, double val5, double val6,double val7) {
		
		Connection conn = MyConnection.getMyConnection();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, year);
			ps.setDouble(2, val1);
			ps.setDouble(3, val2);
			ps.setDouble(4, val3);
			ps.setDouble(5, val4);
			ps.setDouble(6, val5);
			ps.setDouble(7, val6);
			ps.setDouble(8, val7);
			ps.executeUpdate(query);
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	


}
