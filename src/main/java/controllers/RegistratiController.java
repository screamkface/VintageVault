package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import beans.UserBean;
import dao.UserDao;

@WebServlet("/registrati")
public class RegistratiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("registrati.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		List<String> errors = new ArrayList<>();
		RequestDispatcher registerDispatcher = request.getRequestDispatcher("registrati.jsp");

		// errors handling
		if (email == null || email.trim().isEmpty()) {
			errors.add("Il campo email non può essere vuoto!");
		}
		if (password == null || password.trim().isEmpty()) {
			errors.add("Il campo password non può essere vuoto!");
		}
		if (nome == null || nome.trim().isEmpty()) {
			errors.add("Il campo nome non può essere vuoto!");
		}
		if (cognome == null || cognome.trim().isEmpty()) {
			errors.add("Il campo cognome non può essere vuoto!");
		}

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			registerDispatcher.forward(request, response);
			return;
		}

		UserDao userDao = new UserDao((DataSource) getServletContext().getAttribute("DataSource"));

		try {
			if (userDao.checkUserEmailExistance(email)) {
				errors.add("L'email è già in uso!");
				request.setAttribute("errors", errors);
				registerDispatcher.forward(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		UserBean ub = new UserBean();
		ub.setNome(nome);
		ub.setCognome(cognome);
		ub.setEmail(email);
		ub.setPassword(password);
		request.setAttribute("registrazione_completata", true);
		request.getRequestDispatcher("accedi.jsp").forward(request, response);
		try {
			userDao.doSave(ub);

		} catch (SQLException e) {
			LOGGER.log("context",e);
		}
	}

}
