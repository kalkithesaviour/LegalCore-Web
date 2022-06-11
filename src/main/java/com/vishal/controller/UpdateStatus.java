package com.vishal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.vishal.model.DAO;

/**
 * Servlet implementation class UpdateStatus
 */
public class UpdateStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String status = request.getParameter("status");
			String type = request.getParameter("type");
			DAO d = new DAO();
			String result = d.updateStatus(email, status, type);
			if (result.equalsIgnoreCase("success")) {
				response.sendRedirect("AdminHome.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
