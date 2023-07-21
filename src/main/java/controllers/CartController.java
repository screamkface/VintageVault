package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dao.ProductDao;
import others.Cart;

@WebServlet("/carrello")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart carrello = (Cart) request.getSession().getAttribute("carrello");
		if (carrello == null) {
			carrello = new Cart();
			request.getSession().setAttribute("carrello", carrello);
		}

		String action = request.getParameter("action");
		String id_str = request.getParameter("id");
		String redirect = request.getParameter("redirect");

		if (action != null && id_str != null) {
			int id = Integer.parseInt(id_str);
			ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));

			switch (action) {
			case "add": {
				try {
					carrello.addProduct(productDao.getProductById(id));
				} catch (SQLException e) {
					LOGGER.log("context", e);
				}
				break;
			}
			case "delete": {
				try {
					carrello.deleteProduct(productDao.getProductById(id));
				} catch (SQLException e) {
					LOGGER.log("context", e);
				}
				break;
			}
			}
		}

		if (redirect != null && redirect.equals("negozio")) {
			response.sendRedirect("negozio.jsp");
			return;
		}
		if (redirect != null && redirect.equals("carrello")) {
			response.sendRedirect("carrello.jsp");
			return;
		}

		request.setAttribute("carrello", carrello);
		request.getRequestDispatcher("/carrello.jsp").forward(request, response);
		return;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
