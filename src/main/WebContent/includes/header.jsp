<%@page import="others.Cart"%>
<%@page import="beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<%
		UserBean ub = (UserBean) session.getAttribute("user");
		Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
		if(isAdmin==null)
			isAdmin=false;
		
		Cart cart = (Cart) session.getAttribute("carrello");
	%>
	
<!DOCTYPE html>
<html lang="it">
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/styles/header.css" />
<script src="${pageContext.request.contextPath}/assets/js/header.js"
	defer></script>
</head>
<body>
	<header>
		<a href="${pageContext.request.contextPath}/"> <span class="logo-wrapper"> 
				<p>Vintage Vault</p>
		</span>
		</a>

		<nav class="navbar">
			<ul>
				<li><a href="${pageContext.request.contextPath}/negozio.jsp?c=nuovo">NEGOZIO</a></li>
			</ul>
		</nav>

		<div class="icons-wrapper">
			<div style="position: relative">
				<div class="account-popup">
					<% if(ub!=null){ %>
						<span class="account-popup-name"><%=ub.getNome()+" "+ub.getCognome() %></span>
						<hr />
						<ul>
							<li><a href="${pageContext.request.contextPath}/i-miei-ordini">I miei ordini</a></li>
							<% if(isAdmin==true){ %>
							<li><a href="${pageContext.request.contextPath}/admin/gestione-prodotti.jsp">GESTIONE PRODOTTI</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/ordini.jsp">VISUALIZZA ORDINI</a></li>
							<% } %>
						</ul>
						<hr />
						<span class="account-popup-logout"><a href="${pageContext.request.contextPath}/logout">Logout</a></span>
					<% }else{ %>
						<ul>
							<li><a href="${pageContext.request.contextPath}/accedi.jsp">Accedi</a></li>
							<li><a href="${pageContext.request.contextPath}/registrati.jsp">Registrati</a></li>
						</ul>
					<% } %>
				</div>
				<!-- account icon -->
				<svg width="50" height="50" viewBox="0 0 50 50" fill="none"
					xmlns="http://www.w3.org/2000/svg">
            <path
						d="M12.1875 35.625C13.9584 34.2709 15.9375 33.2028 18.125 32.4209C20.3125 31.6389 22.6042 31.2486 25 31.25C27.3959 31.25 29.6875 31.641 31.875 32.4229C34.0625 33.2049 36.0417 34.2722 37.8125 35.625C39.0278 34.2014 39.9743 32.5868 40.6521 30.7813C41.3299 28.9757 41.6681 27.0486 41.6667 25C41.6667 20.382 40.0438 16.4493 36.7979 13.2021C33.5521 9.95488 29.6195 8.33196 25 8.33335C20.382 8.33335 16.4493 9.95627 13.2021 13.2021C9.95488 16.4479 8.33196 20.3806 8.33335 25C8.33335 27.0486 8.67224 28.9757 9.35002 30.7813C10.0278 32.5868 10.9736 34.2014 12.1875 35.625ZM25 27.0834C22.9514 27.0834 21.2236 26.3806 19.8167 24.975C18.4097 23.5695 17.707 21.8417 17.7084 19.7917C17.7084 17.7431 18.4111 16.0153 19.8167 14.6084C21.2222 13.2014 22.95 12.4986 25 12.5C27.0486 12.5 28.7764 13.2028 30.1834 14.6084C31.5903 16.0139 32.2931 17.7417 32.2917 19.7917C32.2917 21.8403 31.5889 23.5681 30.1834 24.975C28.7778 26.382 27.05 27.0847 25 27.0834ZM25 45.8334C22.1181 45.8334 19.4097 45.2861 16.875 44.1917C14.3403 43.0972 12.1354 41.6132 10.2604 39.7396C8.38544 37.8646 6.90141 35.6597 5.80835 33.125C4.7153 30.5903 4.16808 27.882 4.16669 25C4.16669 22.1181 4.71391 19.4097 5.80835 16.875C6.9028 14.3403 8.38683 12.1354 10.2604 10.2604C12.1354 8.38544 14.3403 6.90141 16.875 5.80835C19.4097 4.7153 22.1181 4.16808 25 4.16669C27.882 4.16669 30.5903 4.71391 33.125 5.80835C35.6597 6.9028 37.8646 8.38683 39.7396 10.2604C41.6146 12.1354 43.0993 14.3403 44.1938 16.875C45.2882 19.4097 45.8347 22.1181 45.8334 25C45.8334 27.882 45.2861 30.5903 44.1917 33.125C43.0972 35.6597 41.6132 37.8646 39.7396 39.7396C37.8646 41.6146 35.6597 43.0993 33.125 44.1938C30.5903 45.2882 27.882 45.8347 25 45.8334Z"
						fill="white" />
          </svg>
			</div>
			<a href="${pageContext.request.contextPath}/carrello"> <!-- cart icon -->
				<div>
					<svg width="42" height="44" viewBox="0 0 42 44" fill="currentColor"
						xmlns="http://www.w3.org/2000/svg">
              <path
							d="M12.1528 40.345C11.1979 40.345 10.3802 39.9868 9.69963 39.2705C9.01907 38.5541 8.67937 37.694 8.68053 36.6901C8.68053 35.6849 9.02081 34.8242 9.70136 34.1078C10.3819 33.3914 11.199 33.0339 12.1528 33.0351C13.1076 33.0351 13.9253 33.3933 14.6059 34.1096C15.2864 34.826 15.6261 35.6862 15.625 36.6901C15.625 37.6952 15.2847 38.5559 14.6041 39.2723C13.9236 39.9887 13.1065 40.3462 12.1528 40.345ZM29.5139 40.345C28.559 40.345 27.7413 39.9868 27.0607 39.2705C26.3802 38.5541 26.0405 37.694 26.0416 36.6901C26.0416 35.6849 26.3819 34.8242 27.0625 34.1078C27.743 33.3914 28.5602 33.0339 29.5139 33.0351C30.4687 33.0351 31.2864 33.3933 31.967 34.1096C32.6475 34.826 32.9872 35.6862 32.9861 36.6901C32.9861 37.6952 32.6458 38.5559 31.9653 39.2723C31.2847 39.9887 30.4676 40.3462 29.5139 40.345ZM10.6771 11.1053L14.8437 20.2427H26.9965L31.7708 11.1053H10.6771ZM6.29338 31.2076L11.4583 21.3392L5.20831 7.45029H1.73608V3.79532H7.37844L9.02775 7.45029H37.6736L29.0364 23.8977H14.0625L12.1528 27.5526H32.9861V31.2076H6.29338Z"
							fill="white" />
            </svg>	
					<span class="cart-count"> <%= cart != null ? cart.getItemsCount():0%> </span>
				</div>
			</a>
			<div class="hamburger-menu-icon">
				<!-- hamburger menu icon -->
				<svg width="45" height="45" viewBox="0 0 45 45" fill="none"
					xmlns="http://www.w3.org/2000/svg">
            <path
						d="M7.50001 33.75C6.96876 33.75 6.52313 33.57 6.16313 33.21C5.80313 32.85 5.62376 32.405 5.62501 31.875C5.62501 31.3438 5.80501 30.8981 6.16501 30.5381C6.52501 30.1781 6.97001 29.9988 7.50001 30H37.5C38.0312 30 38.4769 30.18 38.8369 30.54C39.1969 30.9 39.3763 31.345 39.375 31.875C39.375 32.4063 39.195 32.8519 38.835 33.2119C38.475 33.5719 38.03 33.7513 37.5 33.75H7.50001ZM7.50001 24.375C6.96876 24.375 6.52313 24.195 6.16313 23.835C5.80313 23.475 5.62376 23.03 5.62501 22.5C5.62501 21.9688 5.80501 21.5231 6.16501 21.1631C6.52501 20.8031 6.97001 20.6238 7.50001 20.625H37.5C38.0312 20.625 38.4769 20.805 38.8369 21.165C39.1969 21.525 39.3763 21.97 39.375 22.5C39.375 23.0313 39.195 23.4769 38.835 23.8369C38.475 24.1969 38.03 24.3763 37.5 24.375H7.50001ZM7.50001 15C6.96876 15 6.52313 14.82 6.16313 14.46C5.80313 14.1 5.62376 13.655 5.62501 13.125C5.62501 12.5938 5.80501 12.1481 6.16501 11.7881C6.52501 11.4281 6.97001 11.2488 7.50001 11.25H37.5C38.0312 11.25 38.4769 11.43 38.8369 11.79C39.1969 12.15 39.3763 12.595 39.375 13.125C39.375 13.6563 39.195 14.1019 38.835 14.4619C38.475 14.8219 38.03 15.0013 37.5 15H7.50001Z"
						fill="white" />
          </svg>
			</div>
		</div>
		<!-- invisible -->
		<div class="hamburger-menu">
			<div class="hamburger-header">				
				<div class="hamburger-menu-icon">
					<!-- X icon -->
					<svg width="58" height="58" viewBox="0 0 58 58" fill="none"
						xmlns="http://www.w3.org/2000/svg">
              		<path
							d="M29.0001 25.5829L40.9626 13.6204L44.3821 17.0399L32.4196 29.0024L44.3821 40.9649L40.9626 44.3821L29.0001 32.4196L17.0376 44.3821L13.6228 40.9625L25.5853 29L13.6228 17.0375L17.0376 13.6252L29.0001 25.5877V25.5829Z"
							fill="white" />
            </svg>
				</div>
			</div>
			<div class="hamburger-middle-content">
				<div class="hamburger-middle-content-item">
					<a href="${pageContext.request.contextPath}/negozio.jsp?c=nuovo">NEGOZIO</a>
				</div>
				<div class="hamburger-middle-content-item">
					<a href="${pageContext.request.contextPath}/negozio.jsp?c=warehouse">WAREHOUSE</a>
				</div>
			</div>
		</div>
	</header>
</body>
</html>