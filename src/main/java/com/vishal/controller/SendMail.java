package com.vishal.controller;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Properties;

import com.vishal.model.DAO;

/**
 * Servlet implementation class SendMail
 */
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String type = request.getParameter("type");
			String receiverEmail = request.getParameter("email");
			DAO d = new DAO();
			String receiverPassword = d.getPassword(receiverEmail, type);

			// mail sending code: starts
			try {
				final String senderEmail = ""; // yahoo email
				final String senderPassword = ""; // yahoo one-time use generated password
				final String subject = "Password for LegalCore App.";
				final String body = "Your Email ID: " + receiverEmail + ". Your Password: " + receiverPassword;

				Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.mail.yahoo.com");
				prop.put("mail.smtp.port", "587");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.starttls.enable", "true");

				Session ses = Session.getInstance(prop, new jakarta.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(senderEmail, senderPassword);
					}
				});
				Message message = new MimeMessage(ses);
				message.setFrom(new InternetAddress(senderEmail));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
				message.setSubject(subject);
				message.setContent(body, "text/html");

				Transport.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// mail sending code: ends

			HttpSession session = request.getSession();
			session.setAttribute("msg", "Password Sent Successfully!");
			if (type.equalsIgnoreCase("user")) {
				response.sendRedirect("User.jsp");
			} else {
				response.sendRedirect("Advocate.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
