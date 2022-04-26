package com.vishal.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {

	private Connection c;
	
	public DAO() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		c = DriverManager.getConnection("jdbc:mysql://localhost:3306/legalcoreweb","root","vishal#24");
	}
	
	public String checkAdminLogin(String id, String password) throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM admin WHERE id = ? AND password = ?");
		p.setString(1, id);
		p.setString(2, password);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			String n = rs.getString("name");
			c.close();
			return n;
		} else {
			c.close();
			return null;
		}
	}

}
