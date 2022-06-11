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

	public List<Map<String, Object>> getAdvocatesByCategory(String category, String email) throws Exception {
		PreparedStatement p = c
				.prepareStatement("SELECT * FROM advocates WHERE category = ? AND status = 'active' AND email != ?");
		p.setString(1, category);
		p.setString(2, email);
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
		c.close();
		return advocates;
	}

	public List<Map<String, Object>> getAdvocates() throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM advocates");
		ResultSet rs = p.executeQuery();
		List<Map<String, Object>> advocates = new ArrayList<>();
		while (rs.next()) {
			Map<String, Object> advocate = new HashMap<>();
			advocate.put("email", rs.getString("email"));
			advocate.put("name", rs.getString("name"));
			advocate.put("phone", rs.getString("phone"));
			advocate.put("address", rs.getString("address"));
			advocate.put("experience", rs.getInt("experience"));
			advocate.put("category", rs.getString("category"));
			advocate.put("status", rs.getString("status"));
			advocates.add(advocate);
		}
		c.close();
		return advocates;
	}

	public List<Map<String, Object>> getUsers() throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM users");
		ResultSet rs = p.executeQuery();
		List<Map<String, Object>> users = new ArrayList<>();
		while (rs.next()) {
			Map<String, Object> user = new HashMap<>();
			user.put("email", rs.getString("email"));
			user.put("name", rs.getString("name"));
			user.put("phone", rs.getString("phone"));
			user.put("address", rs.getString("address"));
			user.put("status", rs.getString("status"));
			users.add(user);
		}
		c.close();
		return users;
	}

	public List<Map<String, Object>> getAdviceByUserAdvocate(String u_email, String a_email) throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM advices WHERE u_email = ? AND a_email = ?");
		p.setString(1, u_email);
		p.setString(2, a_email);
		ResultSet rs = p.executeQuery();
		List<Map<String, Object>> advices = new ArrayList<>();
		while (rs.next()) {
			Map<String, Object> advice = new HashMap<>();
			advice.put("id", rs.getInt("id"));
			advice.put("query", rs.getString("query"));
			advice.put("q_date", rs.getString("q_date"));
			advice.put("reply", rs.getString("reply"));
			advice.put("r_date", rs.getString("r_date"));
			advices.add(advice);
		}
		c.close();
		return advices;
	}

	public List<String> getAdvocatesByUser(String u_email) throws Exception {
		PreparedStatement p = c
				.prepareStatement("SELECT DISTINCT a_email FROM advices WHERE u_email = ? ORDER BY a_email");
		p.setString(1, u_email);
		ResultSet rs = p.executeQuery();
		List<String> advocates = new ArrayList<>();
		while (rs.next()) {
			advocates.add(rs.getString("a_email"));
		}
		c.close();
		return advocates;
	}

	public List<String> getUsersByAdvocate(String a_email) throws Exception {
		PreparedStatement p = c
				.prepareStatement("SELECT DISTINCT u_email FROM advices WHERE a_email = ? ORDER BY u_email");
		p.setString(1, a_email);
		ResultSet rs = p.executeQuery();
		List<String> users = new ArrayList<>();
		while (rs.next()) {
			users.add(rs.getString("u_email"));
		}
		c.close();
		return users;
	}

	public Map<String, String> getUserByEmail(String u_email) throws Exception {
		PreparedStatement p = c.prepareStatement("SELECT * FROM users WHERE email = ?");
		p.setString(1, u_email);
		ResultSet rs = p.executeQuery();
		Map<String, String> user = null;
		while (rs.next()) {
			user = new HashMap<>();
			user.put("name", rs.getString("name"));
			user.put("phone", rs.getString("phone"));
			user.put("address", rs.getString("address"));
		}
		c.close();
		return user;
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
				"INSERT INTO users (email, name, phone, address, photo, password, status) VALUES (?, ?, ?, ?, ?, ?, 'active')");
		p.setString(1, (String) user.get("email"));
		p.setString(2, (String) user.get("name"));
		p.setString(3, (String) user.get("phone"));
		p.setString(4, (String) user.get("address"));
		p.setBinaryStream(5, (InputStream) user.get("photo"));
		p.setString(6, (String) user.get("password"));
		try {
			p.executeUpdate();
			c.close();
			return "success";
		} catch (SQLIntegrityConstraintViolationException e) {
			c.close();
			return "failed";
		}
	}

	public String askAdvice(String a_email, String u_email, String query) throws Exception {
		PreparedStatement p = c.prepareStatement(
				"INSERT INTO advices (a_email, u_email, query, q_date) VALUES (?, ?, ?, CURRENT_DATE)");
		p.setString(1, a_email);
		p.setString(2, u_email);
		p.setString(3, query);
		try {
			p.executeUpdate();
			c.close();
			return "success";
		} catch (Exception e) {
			c.close();
			return "failed";
		}
	}

	public String replyAdvice(int id, String reply) throws Exception {
		PreparedStatement p = c.prepareStatement("UPDATE advices SET reply = ?, r_date = CURRENT_DATE WHERE id = ?");
		p.setString(1, reply);
		p.setInt(2, id);
		try {
			p.executeUpdate();
			c.close();
			return "success";
		} catch (Exception e) {
			c.close();
			return "failed";
		}
	}

	public String updateStatus(String email, String status, String type) throws Exception {
		PreparedStatement p;
		if (type.equalsIgnoreCase("advocate")) {
			p = c.prepareStatement("UPDATE advocates SET status = ? WHERE email = ?");
		} else {
			p = c.prepareStatement("UPDATE users SET status = ? WHERE email = ?");
		}
		p.setString(1, status);
		p.setString(2, email);
		try {
			p.executeUpdate();
			c.close();
			return "success";
		} catch (Exception e) {
			c.close();
			return "failed";
		}
	}

	public String registerAdvocate(Map<String, Object> advocate) throws Exception {
		PreparedStatement p = c.prepareStatement(
				"INSERT INTO advocates (email, name, phone, address, photo, experience, password, category, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'active')");
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
			c.close();
			return "success";
		} catch (SQLIntegrityConstraintViolationException e) {
			c.close();
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

	public String getPassword(String email, String type) throws Exception {
		PreparedStatement p;
		if (type.equalsIgnoreCase("advocate")) {
			p = c.prepareStatement("SELECT password FROM advocates WHERE email = ?");
		} else {
			p = c.prepareStatement("SELECT password FROM users WHERE email = ?");
		}
		p.setString(1, email);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			String pass = rs.getString("password");
			c.close();
			return pass;
		} else {
			c.close();
			return null;
		}
	}

}
