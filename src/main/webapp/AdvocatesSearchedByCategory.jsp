<%@ page import ="java.util.List" %>
<%@ page import ="java.util.Map" %>

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
        <nav id="my-nav">
            <a href="index.jsp">Home</a>
            <a href="Advocate.jsp">Advocate</a>
            <a href="User.jsp">User</a>
        </nav>
        <div data-aos="fade-right" data-aos-duration="1000">
            <h1>Trust And <br/> <span>Client Focus</span></h1>
        </div>
    </header>
    
    <section class="container my-5">
    	<%
    		String category = (String) request.getAttribute("category");
    		@SuppressWarnings("unchecked")
    		List<Map<String, Object>> advocates = (List<Map<String, Object>>) request.getAttribute("advocates");
    	%>
    	<h4>Search Results for Advocates of <span class="text-primary">"<%= category %>"</span> category</h4>
    	<%
    		for (Map<String, Object> advocate : advocates) {
		%>    			
				<div class="bg-warning p-3 my-3" style="height: 140px;">
					<div style="float:left;">
						<p>
							Name: <%= advocate.get("name") %>
							Email: <%= advocate.get("email") %> 
							Phone: <%= advocate.get("phone") %>
							Experience: <%= advocate.get("experience") %>
						</p>
						<p>Address: <%= advocate.get("address") %></p>
					</div>
					<div style="float:right;">
						<img src="GetPhoto?email=<%= advocate.get("email") %>&type=advocate" alt="" height="100px" />
					</div>
				</div>
    	<%	
    		}
    	%>
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

