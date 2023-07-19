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

@WebServlet("/accedi")
public class AccediController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("accedi.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		List<String> errors = new ArrayList<>();
		RequestDispatcher loginDispatcher = request.getRequestDispatcher("accedi.jsp");

		// errors handling
		if (email == null || email.trim().isEmpty()) {
			errors.add("Il campo email non può essere vuoto!");
		}
		if (password == null || password.trim().isEmpty()) {
			errors.add("Il campo password non può essere vuoto!");
		}

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			loginDispatcher.forward(request, response);
			return;
		}

		UserDao userDao = new UserDao((DataSource) getServletContext().getAttribute("DataSource"));

		try {
			UserBean ub = userDao.doRetrieveByEmailAndPass(email, password);
			if (ub != null) {
				request.getSession().setAttribute("user", ub);
				if (userDao.checkUserIsAdmin(ub.getId())) {
					request.getSession().setAttribute("isAdmin", true);
				}
				response.sendRedirect(request.getContextPath() + "/");
			} else {
				errors.add("Credenziali sbagliate!");
				request.setAttribute("errors", errors);
				loginDispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
