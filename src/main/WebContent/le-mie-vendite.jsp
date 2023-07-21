<%@page import="beans.*"%>
<%@page import="beans.ProductBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%
	List<ProductWarehouseBean> pbs = (List<ProductWarehouseBean>) request.getAttribute("products");
	if(pbs==null) {
		response.sendRedirect("le-mie-vendite");
		return;
	}
	System.out.println(pbs);
%>

<!DOCTYPE html>
<html lang="it">
  <head>	
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="./assets/styles/fonts.css" />
    <link
      rel="stylesheet"
      href="./assets/styles/GestioneProdotti.css"
    />
    <title>Vintage Vault - Le mie vendite</title>
  </head>
  <body>
  <jsp:include page="/includes/header.jsp"></jsp:include>
  	<section>
	    <form
	      id="form-add-product"
	      action="le-mie-vendite"
	      method="POST"
	      enctype="multipart/form-data"
	    >
	      <h3>Vendi un <span>prodotto</span>!</h3>
	      <div class="input-wrapper">
	        <label for="nome">Nome</label>
	        <input type="text" id="nome" name="nome" required />
	      </div>
	
	      <div class="input-wrapper">
	        <label for="descrizione">Descrizione</label>
	        <textarea name="descrizione" id="descrizione" cols="100" required></textarea>
	      </div>
	      <div class="input-wrapper-col">
	        <div class="input-wrapper">
	          <label for="tipo">Tipo:</label>
	          <select name="tipo" id="tipo">
	            <option value="basso">Basso</option>
	            <option value="chitarra">Chitarra</option>
	            <option value="strumento-a-corda">Strumento a corda</option>
	            <option value="batteria">Batteria</option>
	            <option value="fiato">Fiato</option>
	            <option value="pianoforte">Pianoforte</option>
	          </select>
	        </div>
	        <div class="input-wrapper">
	          <label for="prezzo">Prezzo</label>
	          <input type="number" id="prezzo" name="prezzo" step="0.10" required />
	        </div>
	      </div>
	
	      <div class="input-wrapper">
	        <label for="immagine">Immagine</label>
	        <input type="file" id="immagine" name="immagine" required />
	      </div>
	      <input type="submit" value="VENDI" />
	    </form>
	    <div class="prodotti-wrapper">
	      <h3>Le mie vendite</h3>
	      <div class="prodotti-container">
	        <% if(pbs!=null) 
	        for(ProductWarehouseBean pb : pbs){ 
	        %>
	          <div class="card-prodotto">
	            <div class="image-prodotto">
	              <img src="${pageContext.request.contextPath}/getImage?id=<%=pb.getId() %>" alt="immagine" />
	            </div>
	            <div class="info-prodotto">
	              <div class="wrapper-flex-col">
	                <div class="warehouse">
	                  <svg
	                    width="14"
	                    height="14"
	                    viewBox="0 0 14 14"
	                    fill="none"
	                    xmlns="http://www.w3.org/2000/svg"
	                    xmlns:xlink="http://www.w3.org/1999/xlink"
	                  >
	                    <rect width="13.3333" height="13.2692" fill="url(#pattern0)" />
	                    <defs>
	                      <pattern
	                        id="pattern0"
	                        patternContentUnits="objectBoundingBox"
	                        width="1"
	                        height="1"
	                      >
	                        <use
	                          xlink:href="#image0_90_201"
	                          transform="matrix(0.00195312 0 0 0.00196256 0 -0.00241544)"
	                        />
	                      </pattern>
	                      <image
	                        id="image0_90_201"
	                        width="512"
	                        height="512"
	                        xlink:href="data:image/png;base64,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"
	                      />
	                    </defs>
	                  </svg>
	                  <p>Warehouse</p>
	                </div>
	                <div class="prodotto-title"><%=pb.getNome() %></div>
	              </div>
	              <div class="prezzo-prodotto">
	                  <div class="input-wrapper">
	                  	<p class="prezzo">Prezzo: <%=pb.getPrezzo() %></p>
	                  </div>
	                  <p class="stato <%=pb.getStato().equals("IN ATTESA DI REVISIONE")?"bg-orange":pb.getStato().equals("ACCETTATO")?"bg-green":"bg-red"%> ">STATUS: <%=pb.getStato() %></p>
	              </div>
	            </div>
	          </div>
	        <% } %>  
	      </div>
	    </div>
	</section>
	<jsp:include page="/includes/footer.jsp"></jsp:include>
  </body>
</html>
