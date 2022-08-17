package telco.controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
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

import telco.entities.Package;
import telco.services.PackageService;

@WebServlet ("/GoToEmployeeHome")
public class GoToEmployeeHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine; 
	
	@EJB (name = "telco.services/PackageService")
	private PackageService packageService;
	
	public GoToEmployeeHome () {
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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet in GoToEmployeeHome");
		
		String path = "/WEB-INF/employee-home.html";
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Packages setup
		List<Package> packages = null;
		packages = packageService.findAllPackages();	
		ctx.setVariable("packages", packages);
		
		// TODO rejected order(s) or alert setup
		
		templateEngine.process(path, ctx, response.getWriter());
	}
}
