package com.vishal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.vishal.model.DAO;

/**
 * Servlet implementation class UserRegister
 */
@MultipartConfig
public class UserRegister extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String password = request.getParameter("password");
			Part p = request.getPart("photo");
			InputStream photo = null;
			if (p != null) {
				photo = p.getInputStream();
			}
			Map<String, Object> user = new HashMap<>();
			user.put("email", email);
			user.put("name", name);
			user.put("phone", phone);
			user.put("address", address);
			user.put("password", password);
			user.put("photo", photo);
			DAO d = new DAO();
			String result = d.registerUser(user);
			if (result.equalsIgnoreCase("success")) {
				HttpSession session = request.getSession();
				session.setAttribute("name", name);
				session.setAttribute("email", email);
				response.sendRedirect("UserHome.jsp");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("msg", "Email ID already exists!");
				response.sendRedirect("User.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
