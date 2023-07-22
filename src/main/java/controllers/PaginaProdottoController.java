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

import beans.ProductBean;
import dao.ProductDao;

@WebServlet("/pagina-prodotto")
public class PaginaProdottoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_str = request.getParameter("id");
		int id;
		if (id_str != null) {
			id = Integer.parseInt(id_str);
		}else {
			response.getWriter().write("id must be a number");
			return;
		}

		ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));
		try {
			ProductBean pb = productDao.getProductById(id);
			if(pb==null) {
				request.setAttribute("error", "not found");
			}
			request.setAttribute("product", pb);
			
			request.getRequestDispatcher("pagina-prodotto.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.log("context",e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
