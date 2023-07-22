package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.UserBean;
import dao.OrderDao;
import others.Cart;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String indirizzo = request.getParameter("indirizzo");
		String num_carta = request.getParameter("num_carta");
		String intestatario = request.getParameter("intestatario");
		String cvc = request.getParameter("cvc");

		Cart carrello = (Cart) request.getSession().getAttribute("carrello");
		if (carrello == null) {
			request.getRequestDispatcher("/carrello").forward(request, response);
			return;
		}
		List<String> errors = carrello.getProducts().stream()
				.map(el -> el.getProductBean().getQuantita() < el.getQuantita()
						? "Non ci sono abbastanza " + el.getProductBean().getNome() + " nel magazzino (Restanti: "
								+ el.getProductBean().getQuantita() + ")"
						: null)
				.filter(x -> x != null).collect(Collectors.toList());

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/carrello").forward(request, response);
			return;
		}

		OrderDao orderDao = new OrderDao((DataSource) getServletContext().getAttribute("DataSource"));
		try {
			int id_utente = ((UserBean) request.getSession().getAttribute("user")).getId();
			orderDao.effettuaOrdine(carrello.getProducts(), id_utente, indirizzo);
			request.getSession().setAttribute("carrello", null);

			String message = "Acquisto effettuato con successo!";
			String alertScript = "<script>alert('" + message + "')</script>";
			response.getWriter().println(alertScript);

			// Reindirizza l'utente alla pagina del carrello

		} catch (SQLException e) {
			LOGGER.log("context",e);
		}

		response.getWriter().println("<script>window.location.replace('carrello.jsp');</script>");
	}

}
