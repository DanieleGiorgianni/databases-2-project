package telco.controllers;

import java.io.IOException;
import java.util.ArrayList;
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

import telco.entities.Order;
import telco.entities.Package;
import telco.entities.User;
import telco.services.OrderService;
import telco.services.PackageService;

@WebServlet ("/GoToHome")
public class GoToHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/PackageService")
	private PackageService packageService;
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;

	public GoToHome() {
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
		System.out.println("doGet in GoToHome");
		
		String path = "/WEB-INF/home.html";
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Packages setup.
		List<Package> packages = new ArrayList<Package>();
		packages = packageService.findAllPackages();	
		ctx.setVariable("packages", packages);
		
		User user = (User) request.getSession().getAttribute("user");
		
		if (user != null) {
			// Failed order(s).
			List<Order> failedOrders = new ArrayList<Order>();
			failedOrders = orderService.findRejectedOrdersByUser(user);
			ctx.setVariable("failedOrders", failedOrders);
		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}
}
