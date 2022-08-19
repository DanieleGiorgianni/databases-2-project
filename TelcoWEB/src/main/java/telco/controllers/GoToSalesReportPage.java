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

import telco.entities.Alert;
import telco.entities.Order;
import telco.entities.User;
import telco.services.AlertService;
import telco.services.OrderService;
import telco.services.UserService;

@WebServlet ("/GoToSalesReportPage")
public class GoToSalesReportPage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/UserService")
	private UserService userService;
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;
	
	@EJB (name = "telco.services/AlertService")
	private AlertService alertService;
	
	public GoToSalesReportPage() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		List<User> insolventUsers = userService.findAllInsolvents();
		ctx.setVariable("insolventUsers", insolventUsers);
		
		List<Order> rejectedOrders = orderService.findRejectedOrders();
		ctx.setVariable("rejectedOrders", rejectedOrders);
		
		List<Alert> totalAlerts = alertService.findAllAlerts();
		ctx.setVariable("totalAlerts", totalAlerts);
		
		//TODO: add reports related to triggers
		
		String path = "/WEB-INF/report.html";
		templateEngine.process(path, ctx, response.getWriter());
	}

}
