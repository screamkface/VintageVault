package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.ProductBean;
import dao.ProductDao;

@WebServlet("/negozio")
public class NegozioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String search = request.getParameter("search");
		String condizione = request.getParameter("c");
		String min_price_str = request.getParameter("min_price");
		String max_price_str = request.getParameter("max_price");
		String ordina = request.getParameter("ordina");
		String page_str = request.getParameter("page");
		String[] categorie = request.getParameterValues("cat");
		
		double min_price;
		double max_price;
		int pageNum;
		List<String> cat = new ArrayList<>();
		if(min_price_str==null || min_price_str.equals(""))
			min_price = 0.0;
		else
			min_price = Double.parseDouble(min_price_str);
		
		if(max_price_str==null || max_price_str.equals(""))
			max_price = 0.0;
		else
			max_price = Double.parseDouble(max_price_str);
		
		if(categorie != null)
			cat = Arrays.asList(categorie);
		
		if(page_str == null)
			pageNum = 1;
		else
			pageNum = Integer.parseInt(page_str);

		ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));
		try {
			List<ProductBean> pbs = productDao.getFilteredProducts(search, condizione, min_price, max_price, ordina, cat, 30, pageNum*30-30);
			request.setAttribute("products", pbs);
			request.getRequestDispatcher("/negozio.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.log("context",e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
