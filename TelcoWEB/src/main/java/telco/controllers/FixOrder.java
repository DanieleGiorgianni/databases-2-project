package telco.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import telco.entities.Order;
import telco.entities.User;
import telco.services.AlertService;
import telco.services.OrderService;
import telco.services.SasService;
import telco.services.UserService;

@WebServlet ("/FixOrder")
public class FixOrder extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;
	
	@EJB (name = "telco.services/SasService")
	private SasService sasService;
	
	@EJB (name = "telco.services/UserService")
	private UserService userService;
	
	@EJB (name = "telco.services/AlertService")
	private AlertService alertService;
	
	public FixOrder() {
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
	 * Method for handling payment of a previously failed order.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet in FixOrder");
		
		Integer failedOrderId = Integer.parseInt(request.getParameter("failedOrderId"));
		Order order = null;
		order = orderService.findOrderById(failedOrderId);
		
		User user = null;
		user = order.getUser();
		
		String payment = request.getParameter("payment");
		// Correct payment.
		if (payment.contains("OK")) {
			System.out.println("> Payment OK");
			
			order.setPurchasedate(new Timestamp(System.currentTimeMillis()));
			order.setFails(0);
			order.setValid(true);
			
			orderService.fixOrder(order);
			
			// sas creation (if payment is OK).
			Date deactivationdate = Date.valueOf(order.getStartdate().toLocalDate().plusMonths(order.getValidityfee().getMonths()));
			sasService.createSas(deactivationdate, order, order.getUser());
			
			userService.insolventManager(user);
			
			alertService.alertManager(user, new Timestamp(System.currentTimeMillis()));
		}
		// Failed payment.
		else {
			System.out.println("> Payment FAIL");
			
			order.setPurchasedate(null);
			order.setFails(order.getFails() + 1);
			order.setValid(false);
			
			orderService.fixOrder(order);
			
			// Unnecessary insolvent user setting, already done in ManageOrder.
			
			alertService.alertManager(user, new Timestamp(System.currentTimeMillis()));
		}
		
		String path = getServletContext().getContextPath() + "/GoToHome";
		response.sendRedirect(path);
	}
}
