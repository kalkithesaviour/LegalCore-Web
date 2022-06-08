package com.vishal.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.vishal.model.DAO;

/**
 * Servlet implementation class SearchAdvocate
 */
public class SearchAdvocate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String category = request.getParameter("category");
			DAO d = new DAO();
			List<Map<String, Object>> advocates = d.getAdvocatesByCategory(category);
			request.setAttribute("category", category);
			request.setAttribute("advocates", advocates);
			RequestDispatcher rd = request.getRequestDispatcher("AdvocatesSearchedByCategory.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
