package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import beans.ProductBean;
import dao.ProductDao;

@WebServlet("/admin/gestione-prodotti")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class GestioneProdottiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));
		try {
			List<ProductBean> pbs = productDao.getAllProducts(9999999, 0);
			request.setAttribute("products", pbs);
			request.getRequestDispatcher("/admin/gestione-prodotti.jsp").forward(request, response);
		} catch (SQLException e) {
			LOGGER.log("context",e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		switch(action) {
			case "add": {
				addProduct(request,response);
				break;
			}
			case "edit":{
				editProduct(request,response);
				break;
			}
			case "delete":{
				deleteProduct(request,response);
				break;
			}
		}
		
		doGet(request,response);
	}
	
	private void addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String tipo = request.getParameter("tipo");
		String prezzo = request.getParameter("prezzo");
		String quantita = request.getParameter("quantita");
		InputStream immagine = null;
		List<String> errors = new ArrayList<>();

		if (request.getParts() != null && request.getParts().size() > 0) {
			for (Part part : request.getParts()) {
				if (part.getContentType() != null && part.getContentType().contains("image/")) {
					immagine = part.getInputStream();
				}
			}
		}

		// errors handling
		if (nome == null || nome.trim().isEmpty()) {
			errors.add("Il campo nome non può essere vuoto!");
		}
		if (descrizione == null || descrizione.trim().isEmpty()) {
			errors.add("Il campo descrizione non può essere vuoto!");
		}
		if (tipo == null || tipo.trim().isEmpty()) {
			errors.add("Il tipo descrizione non può essere vuoto!");
		}
		if (prezzo == null || prezzo.trim().isEmpty()) {
			errors.add("Il campo prezzo non può essere vuoto!");
		}
		if (quantita == null || quantita.trim().isEmpty()) {
			errors.add("Il campo quantita non può essere vuoto!");
		}
		if (immagine == null) {
			errors.add("Il campo immagine non può essere vuoto!");
		}

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/admin/gestione-prodotti.jsp").forward(request, response);
			return;
		}

		ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));
		
		ProductBean pb = new ProductBean();
		pb.setNome(nome);
		pb.setDescrizione(descrizione);
		pb.setTipo(tipo);
		pb.setPrezzo(Double.parseDouble(prezzo));
		pb.setQuantita(Integer.parseInt(quantita));
		pb.setImmagineIS(immagine);
		pb.setCondizione("nuovo");
		try {
			productDao.doSave(pb);
		} catch (SQLException e) {
			LOGGER.log("context",e);
		}
	}
	
	private void editProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String prezzo = request.getParameter("prezzo");
		String quantita = request.getParameter("quantita");
		List<String> errors = new ArrayList<>();

		// errors handling
		if (id == null || id.trim().isEmpty()) {
			errors.add("Il campo id non può essere vuoto!");
		}
		if (prezzo == null || prezzo.trim().isEmpty()) {
			errors.add("Il prezzo prezzo non può essere vuoto!");
		}

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/admin/gestione-prodotti.jsp").forward(request, response);
			return;
		}
		
		ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));
		
		try {
			productDao.editProduct(Integer.parseInt(id), Double.parseDouble(prezzo), Integer.parseInt(quantita));
		} catch (SQLException e) {
			LOGGER.log("context",e);
		}
	}
	
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		List<String> errors = new ArrayList<>();
		
		// errors handling
		if (id == null || id.trim().isEmpty()) {
			errors.add("Il campo id non può essere vuoto!");
		}
		
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/admin/gestione-prodotti.jsp").forward(request, response);
			return;
		}
		
		ProductDao productDao = new ProductDao((DataSource) getServletContext().getAttribute("DataSource"));
		
		try {
			productDao.deleteProduct(Integer.parseInt(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
