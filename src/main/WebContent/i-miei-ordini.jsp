<%@page import="beans.ProductBean"%>
<%@page import="beans.OrderBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
List<OrderBean> obs = (List<OrderBean>) request.getAttribute("ordini");
if (obs == null) {
	response.sendRedirect("i-miei-ordini");
	return;
}
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Vintage Vault - I Miei Ordini</title>
<link href="assets/styles/i-miei-ordini.css" rel="stylesheet" />
<link href="assets/styles/fonts.css" rel="stylesheet" />
</head>
<body>

	<jsp:include page="./includes/header.jsp"></jsp:include>

	<section>
		<h1>I miei ordini</h1>
		<div class="items-wrapper">
					<%
					if (obs != null)
						for (OrderBean ob : obs) {
					%>
						<div class="ordine">
							<p style="color: black; font-weight: bold;">NUM ORDINE <%=ob.getIdOrdine() %> | <%=ob.getData() %></p>
							<div class="ordini">
								<% for (ProductBean pb : ob.getPb()) { %>
								<div class="card-prodotto">
									<div class="image-prodotto">
										<a class="link-img-prodotto"
											href="pagina-prodotto?id=<%=pb.getId()%>"> <img
											src="getImage?id=<%=pb.getId()%>" alt="immagine" />
										</a>
									</div>
									<div class="info-prodotto">
										<div class="prodotto-title"><%=pb.getNome()%></div>
										<div class="prezzo-prodotto">
											<span><%=pb.getPrezzo()%>&euro;</span>
											<span>| x<%=pb.getQuantita()%></span>
										</div>
									</div>
								</div>
								<% } %>
							</div>
						</div>
					<% } %>
		</div>	
	</section>

	<jsp:include page="./includes/footer.jsp"></jsp:include>

</body>
</html>