package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.OrderBean;
import beans.ProductBean;
import beans.UserBean;
import dao.OrderDao;

@WebServlet("/i-miei-ordini")
public class OrdiniController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDao orderDao = new OrderDao((DataSource) getServletContext().getAttribute("DataSource"));
		try {
			int id_utente = ((UserBean)request.getSession().getAttribute("user")).getId();
			List<OrderBean> pbs = orderDao.getOrdersFromUser(id_utente);
			request.setAttribute("ordini", pbs);
			request.getRequestDispatcher("/i-miei-ordini.jsp").forward(request, response);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
