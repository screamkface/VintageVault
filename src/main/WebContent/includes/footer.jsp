<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="it">
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/styles/footer.css" />
</head>
<body>
	<footer>
		<div class="footer-row">
			<p style="color: black;" class="footer-row-title">CHI SIAMO</p>
			<p class="footer-row-description">Negli ultimi anni, Vintage Vault si e'
			ingrandita grazie al ritorno dell'interesse della moda vintage nel mondo. Ora siamo 
			l'ecommerce piu' influente sul nostro settore e continiuamo offire prodotti di 
			qualita'.
			</p>
		</div>
		<div class="footer-row">
			<p class="footer-row-title">CONTATTACI</p>
			<p class="footer-row-description">
				Vintage Vault<br /> <br /> P.IVA 999888778<br /> <br />
				Via Dei Vestiti, 19 - Milano<br /> <br /> Tel: +39 366
				6969693<br /> <br /> <br /> Email:
				info@vintagevault.it
			</p>
		</div>
		<div class="footer-row">
			<p class="footer-row-title">CATEGORIE</p>
			<p class="footer-row-description">
				<a href="${pageContext.request.contextPath}/negozio.jsp?cat=jeans">Jeans</a><br /> <br /> <a
					href="${pageContext.request.contextPath}/negozio.jsp?cat=maglietta">Magliette</a><br /> <br /> <a
					href="${pageContext.request.contextPath}/negozio.jsp?cat=giacca">Giacche</a><br />
				<br /> 
				<a href="${pageContext.request.contextPath}/negozio.jsp?cat=felpa">Felpe</a><br /> <br />
			</p>
		</div>
	</footer>
</body>
</html>

