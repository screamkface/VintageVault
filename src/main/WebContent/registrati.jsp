<%@page import="java.util.ArrayList"%>
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
<link rel="stylesheet"
	href="assets/styles/fonts.css" />
<link rel="stylesheet"
	href="assets/styles/LoginSignup.css" />
<script src="${pageContext.request.contextPath}/assets/js/validate.js" defer></script>
<title>Vintage Vault Music Store - Registrati</title>
</head>
<body>
	<jsp:include page="./includes/header.jsp"></jsp:include>
	<section>
		<div class="wrapper">
			<h1>REGISTRATI</h1>
			<% if(errors!=null){ %>
				<div class="errors-wrapper">
					<% for (String error : errors) { %>
					<span>- <%=error%></span>
					<% } %>
				</div>
			<% } %>
			<form action="registrati" method="POST">
				<label for="nome">Nome</label>
				<input type="text" name="nome" id="nome" required pattern="^[A-Za-z]+$" onchange="validateField(this, document.querySelector('.error-nome'), 'Il nome deve contenere solo caratteri alfabetici!')"/>
				<span class="error error-nome"></span>
				
				<label for="cognome">Cognome</label>
				<input type="text" name="cognome" id="cognome" required pattern="^[A-Za-z]+$" onchange="validateField(this, document.querySelector('.error-cognome'), 'Il cognome deve contenere solo caratteri alfabetici!')"/>
				<span class="error error-cognome"></span>
				
				<label for="email">Email</label>
				<input type="email" name="email" id="email" required onchange="validateField(this, document.querySelector('.error-email'), 'L'email non è valida!')"/>
				<span class="error error-cognome"></span>
				
				<label for="password">Password</label>
				<input type="password" name="password" id="password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" onchange="validateField(this, document.querySelector('.error-password'), 'La password deve contenere almeno 8 caratteri, una lettera ed un numero!')"/>
				<span class="error error-password"></span>
				
				<input type="submit" value="REGISTRATI" />
	
		</form>
	
			<span class="no-account"> Hai già  un account? <a
				href="accedi.jsp">Accedi
					subito!</a>
			</span>
		</div>
	</section>
	<jsp:include page="./includes/footer.jsp"></jsp:include>
</body>

</html>

