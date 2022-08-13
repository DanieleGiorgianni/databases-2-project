package telco.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	//Useless? YES, it doesn't contain ant logic related to the database (not an EJB)
//	public void init() throws ServletException {
//		ServletContext servletContext = getServletContext();
//		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
//		templateResolver.setTemplateMode(TemplateMode.HTML);
//		this.templateEngine = new TemplateEngine();
//		this.templateEngine.setTemplateResolver(templateResolver);
//		templateResolver.setSuffix(".html");
//	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet in Logout");

		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
			System.out.println("> Session invalidated");
		}
		
		String path = getServletContext().getContextPath() + "/index.html";
		response.sendRedirect(path);
	}
}
