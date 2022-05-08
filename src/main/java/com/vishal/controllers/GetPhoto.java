package com.vishal.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.vishal.model.DAO;

/**
 * Servlet implementation class GetPhoto
 */
public class GetPhoto extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String type = request.getParameter("type");
			DAO d = new DAO();
			byte[] b = d.getPhoto(email, type);
			response.getOutputStream().write(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
