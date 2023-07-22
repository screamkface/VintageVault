package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserBean;

@WebFilter(filterName = "/LoggedInFilter", urlPatterns = {"/checkout", "/checkout.jsp", "/i-miei-ordini", "/i-miei-ordini.jsp", "/le-mie-vendite", "/le-mie-vendite.jsp"})
public class LoggedInFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		UserBean ub = (UserBean) req.getSession().getAttribute("user");
		if (ub == null) {
			res.sendRedirect(req.getContextPath() + "/");
			return;
		}

		chain.doFilter(request, response);
	}

}
