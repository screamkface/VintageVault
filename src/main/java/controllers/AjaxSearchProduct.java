package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;

import beans.ProductBean;
import dao.ProductDao;

@WebServlet("/AjaxSearchProduct")
public class AjaxSearchProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String search = request.getParameter("search");
        JSONObject json = new JSONObject();
        
        if(search == null) {
        	json.put("error", "search param missing");
        	response.setStatus(400);
        	out.print(json.toString());
        	return;
        }else {
        	ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));
        	try {
				List<ProductBean> pbs = productDao.getSearchProducts(search);
				json.put("success", true);
				json.put("products", pbs);
				out.print(json.toString());
				return;
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
	}

}
