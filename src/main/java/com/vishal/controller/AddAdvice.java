package com.vishal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.vishal.model.DAO;

/**
 * Servlet implementation class AddAdvice
 */
public class AddAdvice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String a_email = request.getParameter("a_email");
			String a_name = request.getParameter("a_name");
			String u_email = request.getParameter("u_email");
			String query = request.getParameter("query");
			DAO d = new DAO();
			String result = d.addAdvice(a_email, u_email, query);
			if (result.equalsIgnoreCase("success")) {
				HttpSession session = request.getSession();
				session.setAttribute("msg", "Query Sent Successfully!");
				response.sendRedirect("GetAdvice.jsp?a_email=" + a_email + "&a_name=" + a_name);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("msg", "Query Sending Failed!");
				response.sendRedirect("GetAdvice.jsp?a_email=" + a_email + "&a_name=" + a_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
