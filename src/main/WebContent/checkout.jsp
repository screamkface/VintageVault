<%@page import="others.Cart"%>
<%@page import="others.CartItem"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<% 
	Cart carrello = (Cart) session.getAttribute("carrello");
	if(carrello == null || carrello.getProducts().size() <= 0){
		response.sendRedirect("carrello");
		return;
	}
%>

<!DOCTYPE html>

<html lang="it">

<head>

<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="assets/styles/fonts.css" />
<link rel="stylesheet" href="assets/styles/checkout.css" />
<title>Vintage Vault - Checkout</title>

</head>
<body>


<jsp:include page="./includes/header.jsp"></jsp:include>


<section>
	<h1>Checkout</h1>
	<div class="wrapper">
		<div class="riepilogo">
			<h3>Riepilogo</h3>
			<ul class="riep-prod">
				<% for(CartItem ci : carrello.getProducts()) { %>
					<li><%=ci.getProductBean().getNome() %> | <%=ci.getProductBean().getPrezzo() %>&euro; | x<%=ci.getQuantita() %></li>
				<% } %>
			</ul>
		</div>
		
		<form action="checkout" method="POST">
			<label for="indirizzo">Indirizzo di consegna: </label>
			<input type="text" name="indirizzo" id="indirizzo" required />
			
			<label for="num_carta">Numero carta: </label>
			<input type="number" name="num_carta" id="num_carta" required />
			
			<label for="intestatario">Intestatario: </label>
			<input type="text" name="intestatario" id="intestatario" required />
			
			<label for="cvc">CVC: </label>
			<input type="number" name="cvc" id="cvc" required />
			
			<button  type="submit">ACQUISTA</button>
		</form>
	</div>
</section>
<jsp:include page="./includes/footer.jsp"></jsp:include>
</body>
</html>
</body>
</html>
