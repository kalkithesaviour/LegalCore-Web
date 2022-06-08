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
 * Servlet implementation class AdvocateRegister
 */
@MultipartConfig
public class AdvocateRegister extends HttpServlet {

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
			int experience = Integer.parseInt(request.getParameter("experience"));
			String password = request.getParameter("password");
			String category = request.getParameter("category");
			Part p = request.getPart("photo");
			InputStream photo = null;
			if (p != null) {
				photo = p.getInputStream();
			}
			Map<String, Object> advocate = new HashMap<>();
			advocate.put("email", email);
			advocate.put("name", name);
			advocate.put("phone", phone);
			advocate.put("address", address);
			advocate.put("password", password);
			advocate.put("photo", photo);
			advocate.put("experience", experience);
			advocate.put("category", category);
			DAO d = new DAO();
			String result = d.registerAdvocate(advocate);
			if (result.equalsIgnoreCase("success")) {
				HttpSession session = request.getSession();
				session.setAttribute("name", name);
				session.setAttribute("email", email);
				response.sendRedirect("AdvocateHome.jsp");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("msg", "Email ID already exists!");
				response.sendRedirect("Advocate.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
