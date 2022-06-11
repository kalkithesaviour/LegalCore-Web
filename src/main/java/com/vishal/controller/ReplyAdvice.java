package com.vishal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.vishal.model.DAO;

/**
 * Servlet implementation class ReplyAdvice
 */
public class ReplyAdvice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String reply = request.getParameter("reply");
			DAO d = new DAO();
			String result = d.replyAdvice(id, reply);
			if (result.equalsIgnoreCase("success")) {
				response.sendRedirect("AdvocateHome.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
