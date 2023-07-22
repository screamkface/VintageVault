<%@page import="java.util.List"%>
<%@page import="others.Cart"%>
<%@page import="others.CartItem"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<% 
	Cart carrello = (Cart) request.getAttribute("carrello");
	if(carrello == null){
		response.sendRedirect("carrello");
		return;
	}
	List<String> errors = (List<String>) request.getAttribute("errors");
	
%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="assets/styles/fonts.css" />
<link rel="stylesheet" href="assets/styles/carrello.css" />
<title>Vintage Vault - Carrello</title>
</head>
<body>



<jsp:include page="./includes/header.jsp"></jsp:include>

<section>


	<h1>Il tuo carrello</h1>
	<% if(errors!=null){ %>
		<div class="errors-wrapper">
			<% for (String error : errors) { %>
				<span>- <%=error%></span>
			<% } %>
		</div>
	<% } %>
	<div class="wrapper-cart">
		<div class="cart-items">
			<% for(CartItem pb : carrello.getProducts()) { %>
				
				
			<div class="card-prodotto">
					<div class="image-prodotto">
						<a class="link-img-prodotto"
							href="pagina-prodotto?id=<%=pb.getProductBean().getId()%>"> <img
							src="getImage?id=<%=pb.getProductBean().getId()%>" alt="immagine" />
						</a>
					</div>
					<div class="info-prodotto">
						<div class="warehouse" <%=pb.getProductBean().getCondizione().equals("nuovo")?"style=\"visibility: hidden\"" : "" %> >
							<svg width="14" height="14" viewBox="0 0 14 14" fill="none"	
								xmlns="http://www.w3.org/2000/svg"
								xmlns:xlink="http://www.w3.org/1999/xlink">
				                  <rect width="13.3333" height="13.2692"
													fill="url(#pattern0)" />
				                  <defs>
				                    <pattern id="pattern0"
													patternContentUnits="objectBoundingBox" width="1" height="1">
				                      <use xlink:href="#image0_90_201"
													transform="matrix(0.00195312 0 0 0.00196256 0 -0.00241544)" />
				                    </pattern>
				                    <image id="image0_90_201" width="512" height="512"
													xlink:href="data:image/png;base64,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" />
				                  </defs>
				              </svg>
							<p>Warehouse</p>
						</div>

						<div class="prodotto-title"><%=pb.getProductBean().getNome()%></div>
						<div class="prezzo-prodotto">
							<span><%=pb.getProductBean().getPrezzo()%>&euro;</span>
							<span>x<%=pb.getQuantita() %></span>
							<a href="carrello?action=delete&id=<%=pb.getProductBean().getId()%>&redirect=carrello">
								<svg width="16" height="18" viewBox="0 0 16 18" fill="none" xmlns="http://www.w3.org/2000/svg">
									<path d="M3 18C2.45 18 1.979 17.804 1.587 17.412C1.195 17.02 0.999333 16.5493 1 16V3H0V1H5V0H11V1H16V3H15V16C15 16.55 14.804 17.021 14.412 17.413C14.02 17.805 13.5493 18.0007 13 18H3ZM13 3H3V16H13V3ZM5 14H7V5H5V14ZM9 14H11V5H9V14Z" fill="black"/>
								</svg>
							</a>
						</div>
					</div>
				</div>
			<% } %>
		</div>
		<div class="cart-checkout">
			<div class="cart-checkout-bg">
				<p>Totale: <%=carrello.getTotale() %>&euro;</p>
				<a href="checkout.jsp">Procedi al pagamento</a>
			</div>
		</div>
	</div>
</section>	
	<jsp:include page="./includes/footer.jsp"></jsp:include>
</body>
</html>