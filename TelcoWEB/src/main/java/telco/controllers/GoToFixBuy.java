package telco.controllers;

import java.io.IOException;

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
import telco.services.OrderService;

@WebServlet ("/GoToFixBuy")
public class GoToFixBuy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;
	
	public GoToFixBuy() {
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
		System.out.println("doGet in GoToFixBuy");
		
		String path = "/WEB-INF/fixbuy.html";
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		Integer failedOrderId = null;
		Order failedOrder = null;
		
		failedOrderId = Integer.parseInt(request.getParameter("failedOrderId"));
		//System.out.println("> failedOrderId = " + failedOrderId);
		failedOrder = orderService.findOrderById(failedOrderId);
		//System.out.println("> failedOrder = " + failedOrder);
		
		if (failedOrder != null) {
			ctx.setVariable("failedOrder", failedOrder);
		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}
}
