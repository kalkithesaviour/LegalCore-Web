package com.vishal.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.vishal.model.DAO;

/**
 * Servlet implementation class AdminLogin
 */
public class AdminLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RequestDispatcher rd = request.getRequestDispatcher("Logout");
			rd.include(request, response);
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			DAO d = new DAO();
			String name = d.checkAdminLogin(id, password);
			if (name == null) {
				HttpSession session = request.getSession();
				session.setAttribute("msg", "Id or Password is wrong!");
				response.sendRedirect("Admin.jsp");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("name", name);
				response.sendRedirect("AdminHome.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
