<%@ page import ="com.vishal.model.DAO" %>
<%@ page import ="java.util.List" %>
<%@ page import ="java.util.Map" %>

<%
	String name = (String) session.getAttribute("name");
	String email = (String) session.getAttribute("email");
	if (email == null) {
		session.setAttribute("msg", "Please Login First!");
		response.sendRedirect("User.jsp");	
	} else {
%>
		<!DOCTYPE html>
		<html>
		
		<head>
		    <title>LegalCore</title>
		    <link rel="icon" href="resource/legalcore-logo.png" />
		    <meta name="viewport" content="width=device-width, initial-scale=1">
		
		    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" />
		    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>
		
		    <!-- fontawesome -->
		    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
		    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
		    <!-- fontawesome END -->
		
		    <!-- AOS css and JS -->
		    <link rel="stylesheet" href="resource/aos/aos.css">
		    <script src="resource/aos/aos.js"></script>
		    <!-- AOS css and JS END-->
		
		    <!-- custom css-->
		    <link rel="stylesheet" href="resource/custom.css" />
		    <!-- custom css END-->
		
		    <meta name="author" content="" />
		    <meta name="description" content="" />
		    <meta name="keywords" content="best legal advice" />
		</head>
		
		<body>
		    <header>
		        <nav class="navbar navbar-expand-md">
		            <a class="navbar-brand" href="index.jsp">
		                <img src="resource/legalcore-logo.png" alt=""> <span id="logo">LegalCore</span>
		            </a>
		            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar" >
		                <i class="fas fa-bars"></i>
		            </button>
		            <div class="collapse navbar-collapse" id="collapsibleNavbar">
		                <div class="navbar-nav ml-auto">
		                    <p>
		                        <i class="fa-solid fa-mobile-screen-button"></i>
		                        <a href="tel:98XXXXXXXX">98XXXXXXXX</a> ,
		                        <a href="tel:98XXXXXXXX">98XXXXXXXX</a>
		                    </p>
		                </div>
		            </div>
		        </nav>
		        <%
					String type = (String) session.getAttribute("type");
					if (name == null) {
				%>
						<nav id="my-nav">
				            <a href="index.jsp">Home</a>
				            <a href="Advocate.jsp">Advocate</a>
				            <a href="User.jsp">User</a>
			        	</nav>
				<%
					} else {
				%>
						<nav id="my-nav">
				            <a href="index.jsp">Home</a>
				            <a href="Logout">Logout</a>
				            <span>Welcome <b> <%= name %> </b> </span>
				            <img src="GetPhoto?email=<%= email %>&type=<%= type %>" alt="" height="50px" />
			        	</nav>
			    <%
					}
				%>
		    </header>
		    
		    <section class="container my-5">
		    	<%
		    		String a_email = request.getParameter("a_email");
		    		String a_name = request.getParameter("a_name");
		    	%>
		    	<p>
		    		<img src="GetPhoto?email=<%= a_email %>&type=advocate" alt="" height="100px" />
		    		<b><%= a_name %></b> [ <b><%= a_email %></b> ]
	    		</p>
		    </section>
		    <section class="container">
		    	<div class="row">
			    	<div class="col bg-info">
			    		<%
			    			DAO d = new DAO();
			    			List<Map<String, Object>> advices = d.getAdviceByUserAdvocate(email, a_email);
			    			for (Map<String, Object> advice : advices) {
			    		%>
			    				<div class="bg-warning p-3 my-2">
			    					<p>
		    							Query: <b><%= advice.get("query") %></b>
		    							Date: <b><%= advice.get("q_date") %></b>
			    					</p>
			    					<%
			    						if (advice.get("reply") != null) {
			    					%>		
					    					<p>
				    							Reply: <b><%= advice.get("reply") %></b>
				    							Date: <b><%= advice.get("r_date") %></b>
					    					</p>
			    					<%
			    						} else {
			    				  	%>
			    							<p>
			    								Reply: <b style="color:blue;">Pending</b>
			    							</p>
					    			<%
			    						}
			    					%>
			    				</div>
			    		<%
			    			}
			    		%>
			    	</div>
			    	<div class="col bg-light">
			    		<form action="AskAdvice" method="post">
							<div class="my-4 col">
								<%
									String msg = (String) session.getAttribute("msg");
									if (msg != null) {
								%>
										<p style="color: green;">
											<%= msg %>
										</p>
								<%
										session.setAttribute("msg", null);
									}
								%>
								<h5>Get New Advice!</h5>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text"><i
											class="fa-solid fa-user"></i></span>
									</div>
									<textarea  cols="3" name="query" maxlength="500" placeholder="Enter your query" 
										class="form-control" required></textarea>
								</div>
							</div>
							<div class="col-sm my-2">
								<input type="hidden" name="a_email" value="<%= a_email %>" />
								<input type="hidden" name="a_name" value="<%= a_name %>" />
								<input type="hidden" name="u_email" value="<%= email %>" />
								<button class="btn btn-primary" type="submit">Submit</button>
							</div>
						</form>
			    	</div>
			    </div>
		    </section>
		    <footer class="container-fluid bg-dark p-4 mt-5">
		        <nav class="navbar text-white">
		            <span>&copy; All rights reserved</span>
		        </nav>
		    </footer>
		</body>
		<script>
		AOS.init();
		</script>
		</html>
<%
	}
%>

