package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.OrderBean;
import dao.OrderDao;

@WebServlet("/admin/ordini")
public class OrdiniAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderDao orderDao = new OrderDao((DataSource) getServletContext().getAttribute("DataSource"));
		try {
			String idOrder = request.getParameter("id");
			String startDate = request.getParameter("data_da");
			String endDate = request.getParameter("data_a");

			List<OrderBean> obs = null;
			if (idOrder != null && !idOrder.isEmpty()) {
				OrderBean order = orderDao.getOrderById(Integer.parseInt(idOrder));
				if (order != null) {
					obs = new ArrayList<>();
					obs.add(order);
				}
			} else if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) { // range
				obs = orderDao.cercaPerData(startDate, endDate);
			} else {
				obs = orderDao.getAllOrders(); // if no filters are present, return all orders
			}

			request.setAttribute("ordini", obs); // Aggiungi la lista "obs" come attributo della richiesta
			request.getRequestDispatcher("/admin/ordini.jsp").forward(request, response);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
