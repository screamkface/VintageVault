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
import beans.ProductWarehouseBean;
import beans.UserBean;
import dao.ProductWarehouseDao;

@WebServlet("/le-mie-vendite")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class LeMieVenditeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductWarehouseDao productDao = new ProductWarehouseDao((DataSource) getServletContext().getAttribute("DataSource"));
		try {
			int id_utente = ((UserBean)request.getSession().getAttribute("user")).getId();
			List<ProductWarehouseBean> pbs = productDao.getRichiestaVenditaByIdUtente(id_utente);
			request.setAttribute("products", pbs);
			request.getRequestDispatcher("le-mie-vendite.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String tipo = request.getParameter("tipo");
		String prezzo = request.getParameter("prezzo");
		
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
		if (immagine == null) {
			errors.add("Il campo immagine non può essere vuoto!");
		}

		if (!errors.isEmpty()) {
			System.out.println("error " + errors);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("le-mie-vendite.jsp").forward(request, response);
			return;
		}

		ProductWarehouseDao productDao = new ProductWarehouseDao((DataSource) getServletContext().getAttribute("DataSource"));
		
		ProductBean pb = new ProductBean();
		pb.setNome(nome);
		pb.setDescrizione(descrizione);
		pb.setTipo(tipo);
		pb.setPrezzo(Double.parseDouble(prezzo));
		pb.setImmagineIS(immagine);
		pb.setCondizione("warehouse");
		try {
			productDao.addRequest(pb, ((UserBean)request.getSession().getAttribute("user")).getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		doGet(request, response);
	}

}
