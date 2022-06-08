package com.vishal.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO {

	private Connection c;

	public DAO() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		c = DriverManager.getConnection("jdbc:mysql://localhost:3306/legalcoreweb", "root", "vishal#24");
	}

	public List<Map<String, Object>> getAdvocatesByCategory(String category) throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM advocates WHERE category = ?");
		p.setString(1, category);
		ResultSet rs = p.executeQuery();
		List<Map<String, Object>> advocates = new ArrayList<>();
		while (rs.next()) {
			Map<String, Object> advocate = new HashMap<>();
			advocate.put("email", rs.getString("email"));
			advocate.put("name", rs.getString("name"));
			advocate.put("phone", rs.getString("phone"));
			advocate.put("address", rs.getString("address"));
			advocate.put("experience", rs.getInt("experience"));
			advocates.add(advocate);
		}
		return advocates;
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

	public String checkUserLogin(String email, String password) throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
		p.setString(1, email);
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

	public String checkAdvocateLogin(String email, String password) throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM advocates WHERE email = ? AND password = ?");
		p.setString(1, email);
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

	public String registerUser(Map<String, Object> user) throws Exception {
		PreparedStatement p = c.prepareStatement(
				"INSERT INTO users (email, name, phone, address, photo, password) VALUES (?, ?, ?, ?, ?, ?)");
		p.setString(1, (String) user.get("email"));
		p.setString(2, (String) user.get("name"));
		p.setString(3, (String) user.get("phone"));
		p.setString(4, (String) user.get("address"));
		p.setBinaryStream(5, (InputStream) user.get("photo"));
		p.setString(6, (String) user.get("password"));
		try {
			p.executeUpdate();
			return "success";
		} catch (SQLIntegrityConstraintViolationException e) {
			return "failed";
		}
	}

	public String registerAdvocate(Map<String, Object> advocate) throws Exception {
		PreparedStatement p = c.prepareStatement(
				"INSERT INTO advocates (email, name, phone, address, photo, experience, password, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		p.setString(1, (String) advocate.get("email"));
		p.setString(2, (String) advocate.get("name"));
		p.setString(3, (String) advocate.get("phone"));
		p.setString(4, (String) advocate.get("address"));
		p.setBinaryStream(5, (InputStream) advocate.get("photo"));
		p.setInt(6, (int) advocate.get("experience"));
		p.setString(7, (String) advocate.get("password"));
		p.setString(8, (String) advocate.get("category"));
		try {
			p.executeUpdate();
			return "success";
		} catch (SQLIntegrityConstraintViolationException e) {
			return "failed";
		}
	}

	public byte[] getPhoto(String email, String type) throws Exception {
		PreparedStatement p;
		if (type.equalsIgnoreCase("advocate")) {
			p = c.prepareStatement("SELECT photo FROM advocates WHERE email = ?");
		} else {
			p = c.prepareStatement("SELECT photo FROM users WHERE email = ?");
		}
		p.setString(1, email);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			byte[] b = rs.getBytes("photo");
			c.close();
			return b;
		} else {
			c.close();
			return null;
		}
	}

}
