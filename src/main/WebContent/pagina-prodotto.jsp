<%@page import="beans.ProductBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<% 
	if(request.getParameter("id")==null){
		response.sendRedirect("index.jsp");
		return;
	}
	ProductBean pb = (ProductBean) request.getAttribute("product");
	
	if(request.getAttribute("error") != null && !request.getAttribute("error").equals("not found"))
		if(pb == null){
			response.sendRedirect("pagina-prodotto?id="+request.getParameter("id"));
			return;
		}
%>
<!DOCTYPE html>

<html lang="it">
    <head>
        <meta charset="UTF-8" />      
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Vintage Vault - Prodotto</title>
        <link href="assets/styles/pagina-prodotto.css" rel="stylesheet" />
        <link href="assets/styles/fonts.css" rel="stylesheet" />
    </head>
    <body>
        <jsp:include page="./includes/header.jsp"></jsp:include>
        <section>
	        <% if(pb != null) { %>
	            <div class="global">
	                <div class="imageBox">
	                    <img src="getImage?id=<%=pb.getId() %>" alt="Immagine del prodotto"/>
	                </div>
	                <div class="infoBox">
	                    <div class="basicText"><%=pb.getNome() %></div>
	                    <div class="info">
	                        <%=pb.getDescrizione() %>
	                    </div>
	                    <div>
	                        <a href="carrello?action=add&id=<%=pb.getId()%>&redirect=carrello" class="buyButton">
	                            <span>ACQUISTA</span>
	                            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M11.5942 9.14286V5.71428H8.11594V3.42857H11.5942V0H13.913V3.42857H17.3913V5.71428H13.913V9.14286H11.5942ZM6.95652 24C6.31884 24 5.77275 23.776 5.31826 23.328C4.86377 22.88 4.63691 22.3421 4.63768 21.7143C4.63768 21.0857 4.86493 20.5474 5.31942 20.0994C5.77391 19.6514 6.31961 19.4278 6.95652 19.4286C7.5942 19.4286 8.14029 19.6526 8.59478 20.1006C9.04928 20.5486 9.27613 21.0865 9.27536 21.7143C9.27536 22.3429 9.04812 22.8811 8.59362 23.3291C8.13913 23.7771 7.59343 24.0008 6.95652 24ZM18.5507 24C17.913 24 17.367 23.776 16.9125 23.328C16.458 22.88 16.2311 22.3421 16.2319 21.7143C16.2319 21.0857 16.4591 20.5474 16.9136 20.0994C17.3681 19.6514 17.9138 19.4278 18.5507 19.4286C19.1884 19.4286 19.7345 19.6526 20.189 20.1006C20.6435 20.5486 20.8703 21.0865 20.8696 21.7143C20.8696 22.3429 20.6423 22.8811 20.1878 23.3291C19.7333 23.7771 19.1876 24.0008 18.5507 24ZM20.8696 18.2857H3.04348L6.49275 12.1143L2.31884 3.42857H0V1.14286H3.7971L8.72464 11.4286H16.8696L21.3623 3.42857H24L18.2319 13.7143H8.23188L6.95652 16H20.8696V18.2857Z" fill="white"></path>
                  </svg>
	                        </a>
	                        <p>Restanti: <%=pb.getQuantita() %></p>
	                    </div>
	                </div>
	            </div>
	         <% } else {%>
	         	<div class="global">
	         		<h1>ITEM NOT FOUND</h1>
	         	</div>
	         <% } %>
        </section>
        
        <jsp:include page="./includes/footer.jsp"></jsp:include>
    </body>
    
</html>