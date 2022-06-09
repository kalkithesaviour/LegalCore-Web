package com.vishal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.vishal.model.DAO;

/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			DAO d = new DAO();
			String name = d.checkUserLogin(email, password);
			if (name == null) {
				HttpSession session = request.getSession();
				session.setAttribute("msg", "Email-Id or Password is wrong!");
				response.sendRedirect("User.jsp");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("name", name);
				session.setAttribute("email", email);
				session.setAttribute("type", "user");
				response.sendRedirect("UserHome.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
