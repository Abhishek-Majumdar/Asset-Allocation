package com.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataValues {
	
	public ResultSet fetchData(String query) {
		
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

}
