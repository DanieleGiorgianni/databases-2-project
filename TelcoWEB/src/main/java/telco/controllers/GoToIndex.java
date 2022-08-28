package telco.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@WebServlet ("/GoToIndex")
public class GoToIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	public GoToIndex() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
	
	/*
	 * Method used when a non-logged-in user attempts to purchase a package, 
	 * saves the configuration of that package and creates its session attributes so that, 
	 * once logged in, he or she can terminate the purchase.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet in GoToIndex");
		
		String path = "index.html";
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Importing parameters from buy.html.
		String packageId = request.getParameter("packageId");
		String[] productId = request.getParameterValues("productId");
		String validityfeeId = request.getParameter("validityfeeId");
		String startdate = request.getParameter("startdate");
		
		//Setting session attributes
		request.getSession().setAttribute("orderNoLogin", "yes");
		request.getSession().setAttribute("packageId", packageId);
		request.getSession().setAttribute("productId", productId);
		request.getSession().setAttribute("validityfeeId", validityfeeId);
		request.getSession().setAttribute("startdate", startdate);
		
		templateEngine.process(path, ctx, response.getWriter());
	}
}
