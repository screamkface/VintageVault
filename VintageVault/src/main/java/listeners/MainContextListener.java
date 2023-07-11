package listeners;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MainContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();

		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/vintagevault");
		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
		ctx.setAttribute("DataSource", ds);
		System.out.println("DataSource creation...." + ds.toString());
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
