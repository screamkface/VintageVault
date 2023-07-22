<%@page import="beans.ProductBean"%>
<%@page import="org.owasp.encoder.Encode"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
//filtro condizioni
String f_condizioni = request.getParameter("c");
if (f_condizioni == null)
	f_condizioni = "nuovo";

//filtro categorie
List<String> f_categorie;
String[] f_cat = request.getParameterValues("cat");
if (f_cat == null) {
	f_categorie = new ArrayList<>();
} else {
	f_categorie = Arrays.asList(f_cat);
}

//filtro prezzo
String f_prezzo_min = request.getParameter("min_price");
String f_prezzo_max = request.getParameter("max_price");
if (f_prezzo_min == null)
	f_prezzo_min = "";
if (f_prezzo_max == null)
	f_prezzo_max = "";

//filtro ordinamento
String ordina = request.getParameter("ordina");
if(ordina == null)
	ordina = "";

//filtro search
String search = request.getParameter("search");
if(search == null)
	search = "";

//filtro page
String pageNum_str = request.getParameter("page");
int pageNum;
if(pageNum_str == null)
	pageNum = 1;
else
	pageNum = Integer.parseInt(pageNum_str);
List<ProductBean> pbs = (List<ProductBean>) request.getAttribute("products");
if (pbs == null) {
	String query = "?c=" + Encode.forHtml(f_condizioni) + "&min_price=" + Encode.forHtml(f_prezzo_min) + "&max_price=" + Encode.forHtml(f_prezzo_max) + "&ordina=" + Encode.forHtml(ordina)+ "&page=" + pageNum;
	if (f_cat != null)
		for (String cat : f_cat)
	query += "&cat=" + Encode.forHtml(cat);
	System.out.println(query);
	response.sendRedirect("negozio" + query);
	return;
}

String query = "?c=" + Encode.forHtml(f_condizioni) + "&min_price=" + Encode.forHtml(f_prezzo_min) + "&max_price=" + Encode.forHtml(f_prezzo_max) + "&ordina=" + Encode.forHtml(ordina);
if (f_cat != null)
	for (String cat : f_cat)
		query += "&cat=" + Encode.forHtml(cat);
%>

<!DOCTYPE html>

<html lang="it">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="assets/styles/fonts.css" />
<link rel="stylesheet" href="assets/styles/negozio.css" />
<script src="assets/js/negozio.js" defer></script>
<title>Vintage Vault - Negozio</title>
</head>
<body style="background-color: #008080">
	<jsp:include page="./includes/header.jsp"></jsp:include>
	<section class="wrapper">
		<div class="filtri">
			<form action="negozio.jsp" method="GET">
				<div class="filtro">
					<div class="filtro-content">
			
					</div>
				</div>
				<div class="filtro">
					<p class="filtro-title">Categorie</p>
					<div class="filtro-content">
						<div>
							<input type="checkbox" id="categoria_jeans" name="cat"
								value="jeans"
								<%=f_categorie.contains("jeans") ? "checked" : ""%> /> <label
								for="categoria_jeans">Jeans</label>
						</div>
						<div>
							<input type="checkbox" id="categoria_magliette" name="cat"
								value="maglietta"
								<%=f_categorie.contains("maglietta") ? "checked" : ""%> /> <label
								for="categoria_magliette">Magliette</label>
						</div>
						<div>
							<input type="checkbox" id="categoria_giacche"
								name="cat" value="giacche"
								<%=f_categorie.contains("giacca") ? "checked" : ""%> />
							<label for="categoria_giacche">Giacche</label>
						</div>
						<div>
							<input type="checkbox" id="categoria_felpe" name="cat"
								value="felpa"
								<%=f_categorie.contains("felpa") ? "checked" : ""%> /> <label
								for="categoria_felpe">Felpe</label>
						</div>
							
						
			
					</div>
				</div>
				<div class="filtro">
					<p class="filtro-title">Prezzo</p>
					<div class="filtro-content">
						<div>
							<label for="">Min:</label> <input type="number" id="min_price"
								name="min_price" placeholder="0" value="<%=Encode.forHtml(f_prezzo_min) %>" /> <span>&euro;</span>
						</div>
						<div>
							<label for="filtro_warehouse">Max:</label> <input type="number"
								id="max_price" name="max_price" placeholder="0"
								value="<%=Encode.forHtml(f_prezzo_max) %>" /> <span>&euro;</span>
						</div>
					</div>
				</div>		
				<div class="filtro">
					<p class="filtro-title">Ordina</p>
					<div class="filtro-content">
						<div class="ordinaper">
							<select name="ordina" id="ordina">
								<option value="" <%=ordina.equals("")?"selected":"" %>></option>
								<option value="prezzo_asc" <%=ordina.equals("prezzo_asc")?"selected":"" %>>Prezzo crescente</option>
								<option value="prezzo_desc" <%=ordina.equals("prezzo_desc")?"selected":"" %>>Prezzo descrescente</option>
							</select>
						</div>
					</div>
				</div>

				<button type="submit">APPLICA</button>
			</form>
		</div>
		<div class="risultati">
			<div class="risultati-header">
				<!-- <h2>Risultati</h2>  -->
				<span class="separator"></span>	
				<div class="searchbar" id="searchbar">
					<form action="" autocomplete="off">
						<input type="text" name="search" placeholder="Cerca..."
							autocomplete="off" <%=!search.isBlank() ? "value=" + Encode.forHtml(search) :"" %> />
					</form>
					<!-- search icon -->
					<svg width="23" height="23" viewBox="0 0 23 23" fill="none"
						xmlns="http://www.w3.org/2000/svg">
              <g clip-path="url(#clip0_52_7)">
                <path
							d="M15.9231 9.73077C15.9231 8.02604 15.3172 6.56781 14.1055 5.35607C12.8937 4.14433 11.4355 3.53846 9.73077 3.53846C8.02604 3.53846 6.56781 4.14433 5.35607 5.35607C4.14433 6.56781 3.53846 8.02604 3.53846 9.73077C3.53846 11.4355 4.14433 12.8937 5.35607 14.1055C6.56781 15.3172 8.02604 15.9231 9.73077 15.9231C11.4355 15.9231 12.8937 15.3172 14.1055 14.1055C15.3172 12.8937 15.9231 11.4355 15.9231 9.73077ZM23 21.2308C23 21.7099 22.8249 22.1246 22.4748 22.4748C22.1246 22.8249 21.7099 23 21.2308 23C20.7332 23 20.3185 22.8249 19.9868 22.4748L15.2458 17.7476C13.5964 18.8902 11.758 19.4615 9.73077 19.4615C8.41306 19.4615 7.15294 19.2058 5.95042 18.6944C4.7479 18.183 3.71124 17.4919 2.84044 16.6211C1.96965 15.7503 1.27855 14.7136 0.767127 13.5111C0.255709 12.3086 0 11.0485 0 9.73077C0 8.41306 0.255709 7.15294 0.767127 5.95042C1.27855 4.7479 1.96965 3.71124 2.84044 2.84044C3.71124 1.96965 4.7479 1.27855 5.95042 0.767127C7.15294 0.255709 8.41306 0 9.73077 0C11.0485 0 12.3086 0.255709 13.5111 0.767127C14.7136 1.27855 15.7503 1.96965 16.6211 2.84044C17.4919 3.71124 18.183 4.7479 18.6944 5.95042C19.2058 7.15294 19.4615 8.41306 19.4615 9.73077C19.4615 11.758 18.8902 13.5964 17.7476 15.2458L22.4886 19.9868C22.8295 20.3277 23 20.7424 23 21.2308Z"
							fill="black" />
              </g>
              <defs>
                <clipPath id="clip0_52_7">
                  <rect width="23" height="23" fill="white" />
                </clipPath>
              </defs>
            </svg>
				</div>
				<span class="separator"></span>
				<div class="show-filtri">
					<svg width="27" height="24" viewBox="0 0 27 24" fill="none"
						xmlns="http://www.w3.org/2000/svg">
              <path
							d="M25.6222 4.83615H9.04583M6.03194 4.83615H1.51111M25.6222 19.9056H9.04583M6.03194 19.9056H1.51111M18.0875 12.3709H1.51111M25.6222 12.3709H21.1014M7.53889 1.82227C7.93855 1.82227 8.32185 1.98103 8.60446 2.26364C8.88706 2.54625 9.04583 2.92954 9.04583 3.32921V6.3431C9.04583 6.74277 8.88706 7.12606 8.60446 7.40867C8.32185 7.69128 7.93855 7.85004 7.53889 7.85004C7.13922 7.85004 6.75592 7.69128 6.47332 7.40867C6.19071 7.12606 6.03194 6.74277 6.03194 6.3431V3.32921C6.03194 2.92954 6.19071 2.54625 6.47332 2.26364C6.75592 1.98103 7.13922 1.82227 7.53889 1.82227ZM7.53889 16.8917C7.93855 16.8917 8.32185 17.0505 8.60446 17.3331C8.88706 17.6157 9.04583 17.999 9.04583 18.3987V21.4125C9.04583 21.8122 8.88706 22.1955 8.60446 22.4781C8.32185 22.7607 7.93855 22.9195 7.53889 22.9195C7.13922 22.9195 6.75592 22.7607 6.47332 22.4781C6.19071 22.1955 6.03194 21.8122 6.03194 21.4125V18.3987C6.03194 17.999 6.19071 17.6157 6.47332 17.3331C6.75592 17.0505 7.13922 16.8917 7.53889 16.8917ZM19.5944 9.35699C19.9941 9.35699 20.3774 9.51575 20.66 9.79836C20.9426 10.081 21.1014 10.4643 21.1014 10.8639V13.8778C21.1014 14.2775 20.9426 14.6608 20.66 14.9434C20.3774 15.226 19.9941 15.3848 19.5944 15.3848C19.1948 15.3848 18.8115 15.226 18.5289 14.9434C18.2463 14.6608 18.0875 14.2775 18.0875 13.8778V10.8639C18.0875 10.4643 18.2463 10.081 18.5289 9.79836C18.8115 9.51575 19.1948 9.35699 19.5944 9.35699Z"
							stroke="black" stroke-width="2" stroke-linecap="round"
							stroke-linejoin="round" />
            </svg>				</div>
			</div>
			<div class="risultati-content">
				<%
				if (pbs != null)
					for (ProductBean pb : pbs) {
				%>
				<div class="card-prodotto">
					<div class="image-prodotto">
						<a class="link-img-prodotto"
							href="pagina-prodotto?id=<%=pb.getId()%>"> <img
							src="getImage?id=<%=pb.getId()%>" alt="immagine" />
						</a>
					</div>
					<div class="info-prodotto">
						<div class="warehouse" <%=pb.getCondizione().equals("nuovo")?"style=\"visibility: hidden\"" : "" %> >
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
						<div class="prodotto-title"><%=pb.getNome()%></div>
						<div class="prezzo-prodotto">
							<span><%=pb.getPrezzo()%>&euro;</span> <a
								href="carrello?action=add&id=<%=pb.getId()%>&redirect=negozio"> <svg width="24"
									height="24" viewBox="0 0 24 24" fill="none"
									xmlns="http://www.w3.org/2000/svg">
                    <path
										d="M11.5942 9.14286V5.71428H8.11594V3.42857H11.5942V0H13.913V3.42857H17.3913V5.71428H13.913V9.14286H11.5942ZM6.95652 24C6.31884 24 5.77275 23.776 5.31826 23.328C4.86377 22.88 4.63691 22.3421 4.63768 21.7143C4.63768 21.0857 4.86493 20.5474 5.31942 20.0994C5.77391 19.6514 6.31961 19.4278 6.95652 19.4286C7.5942 19.4286 8.14029 19.6526 8.59478 20.1006C9.04928 20.5486 9.27613 21.0865 9.27536 21.7143C9.27536 22.3429 9.04812 22.8811 8.59362 23.3291C8.13913 23.7771 7.59343 24.0008 6.95652 24ZM18.5507 24C17.913 24 17.367 23.776 16.9125 23.328C16.458 22.88 16.2311 22.3421 16.2319 21.7143C16.2319 21.0857 16.4591 20.5474 16.9136 20.0994C17.3681 19.6514 17.9138 19.4278 18.5507 19.4286C19.1884 19.4286 19.7345 19.6526 20.189 20.1006C20.6435 20.5486 20.8703 21.0865 20.8696 21.7143C20.8696 22.3429 20.6423 22.8811 20.1878 23.3291C19.7333 23.7771 19.1876 24.0008 18.5507 24ZM20.8696 18.2857H3.04348L6.49275 12.1143L2.31884 3.42857H0V1.14286H3.7971L8.72464 11.4286H16.8696L21.3623 3.42857H24L18.2319 13.7143H8.23188L6.95652 16H20.8696V18.2857Z"
										fill="white" />
                  </svg>
							</a>
						</div>
					</div>
				</div>
				
				<%
				}
				%>
			</div>
			<div class="risultati-paging">
				<div class="prev-page">
					<a href="negozio<%=query %>&page=<%=pageNum<=1?1:pageNum-1 %>" <%=pageNum<=1?"data-disabled=true":"" %> >Pagina precedente</a>
				</div>
				<div class="next-page">
					<a href="negozio<%=query %>&page=<%=pageNum+1 %>"  <%=pbs.size()<30?"data-disabled=true":"" %> >Pagina successiva</a>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="./includes/footer.jsp"></jsp:include>
</body>
</html>
