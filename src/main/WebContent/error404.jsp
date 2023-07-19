<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="assets/styles/fonts.css" />
<link rel="stylesheet" href="assets/styles/LoginSignup.css" />
<title>Vintage Vault - Error 404</title>
</head>
<body>
	<jsp:include page="./includes/header.jsp"></jsp:include>
	<section>
		<div class="wrapper">
			<h1>Error 404</h1>
			<div class="errors-wrapper">
				<span style="text-align:center;">Page not found!</span>
			</div>
		</div>
	</section>
	<jsp:include page="./includes/footer.jsp"></jsp:include>
</body>
</html>
