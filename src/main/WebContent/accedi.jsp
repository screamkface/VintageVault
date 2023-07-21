<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%
	List<String> errors = (List<String>) request.getAttribute("errors");
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="assets/styles/fonts.css" />
<link rel="stylesheet" href="assets/styles/LoginSignup.css" />
<title>Vintage Vault - Accedi</title>
</head>
<body>
	<jsp:include page="./includes/header.jsp"></jsp:include>
	<section>
		<div class="wrapper">
			<h1>ACCEDI</h1>
			<% if(errors!=null){ %>
				<div class="errors-wrapper">
					<% for (String error : errors) { %>
					<span>- <%=error%></span>
					<% } %>
				</div>
				
				
			<% } %>
			<% if(((Boolean)request.getAttribute("registrazione_completata"))!=null) { %>
				<div class="success-wrapper">
					<span>Registrazione effettuata con successo!</span>
				</div>
			<% } %>
			<form action="accedi" method="POST">
				<label for="email">Email</label> <input type="email" name="email"
					id="email" required /> <label for="password">Password</label> <input
					type="password" name="password" id="password" required /> <input
					type="submit" value="ACCEDI" />

			</form>

			<span class="no-account">Non hai ancora un
				account? <a href="registrati.jsp">Registrati subito!</a>

			</span>
		</div>
	</section>
	<jsp:include page="./includes/footer.jsp"></jsp:include>
	
</body>

</html>
