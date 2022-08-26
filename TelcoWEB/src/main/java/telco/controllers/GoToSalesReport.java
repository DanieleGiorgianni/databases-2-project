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

import telco.entities.Alert;
import telco.entities.Order;
import telco.entities.User;
import telco.services.AlertService;
import telco.services.OrderService;
import telco.services.UserService;
import telco.triggers.AverageProductsPerPackage;
import telco.triggers.ProductBestSeller;
import telco.triggers.PurchasePerPackage;
import telco.triggers.PurchasePerPackageAndValidityPeriod;
import telco.triggers.TotalSalesPerPackage;
import telco.triggers.TriggersService;

@WebServlet ("/GoToSalesReport")
public class GoToSalesReport extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/UserService")
	private UserService userService;
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;
	
	@EJB (name = "telco.services/AlertService")
	private AlertService alertService;
	
	@EJB (name = "telco.services/TriggersService")
	private TriggersService triggersService;
	
	public GoToSalesReport() {
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
		System.out.println("doGet in GoToSalesReport");
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Obtaining all insolvent users.
		List<User> insolventUsers = new ArrayList<User>();
		insolventUsers = userService.findAllInsolvents();
		ctx.setVariable("insolventUsers", insolventUsers);
		
		// Obtaining all rejected orders.
		List<Order> rejectedOrders = new ArrayList<Order>();
		rejectedOrders = orderService.findRejectedOrders();
		ctx.setVariable("rejectedOrders", rejectedOrders);
		
		// Obtaining all alerts.
		List<Alert> totalAlerts = new ArrayList<Alert>();
		totalAlerts =  alertService.findAllAlerts();
		ctx.setVariable("totalAlerts", totalAlerts);
		
		// Obtaining all purchases per package.
		List<PurchasePerPackage> purchasePerPackages = new ArrayList<PurchasePerPackage>();
		purchasePerPackages = triggersService.findAllPurchasePerPackage();
		ctx.setVariable("purchasePerPackages", purchasePerPackages);
		
		// Obtaining all purchases per package with their validity period.
		List<PurchasePerPackageAndValidityPeriod> purchasePerPackageAndValidityPeriod = new ArrayList<PurchasePerPackageAndValidityPeriod>();
		purchasePerPackageAndValidityPeriod = triggersService.findAllPurchasePerPackageAndValidityPeriod();
		ctx.setVariable("purchasePerPackageAndValidityPeriod", purchasePerPackageAndValidityPeriod);
		
		// Obtaining all sales per package with and without optional products.
		List<TotalSalesPerPackage> totalSalesPerPackage = new ArrayList<TotalSalesPerPackage>();
		totalSalesPerPackage = triggersService.findAllTotalSalesPerPackage();
		ctx.setVariable("totalSalesPerPackage", totalSalesPerPackage);
		
		// Obtaining the average of orders sold with optional products over the total for that package.
		List<AverageProductsPerPackage> averageProductsPerPackage = new ArrayList<AverageProductsPerPackage>();
		averageProductsPerPackage = triggersService.findAllAverageProductsPerPackage();
		ctx.setVariable("averageProductsPerPackage", averageProductsPerPackage);
		
		// Obtaining the most sold optional products.
		List<ProductBestSeller> productBestSeller = new ArrayList<ProductBestSeller>();
		productBestSeller = triggersService.findAllProductBestSeller();
		if (!productBestSeller.isEmpty()) {
			int maxProductSales = -1;
			ProductBestSeller pBest = null;
			for (ProductBestSeller pb : productBestSeller) {
				if (pb.getProductsales() > maxProductSales) {
					maxProductSales = pb.getProductsales();
					pBest = pb;
				}
			}
			productBestSeller.clear();
			productBestSeller.add(pBest);	
		}
		ctx.setVariable("productBestSeller", productBestSeller);
		
		String path = "/WEB-INF/report.html";
		templateEngine.process(path, ctx, response.getWriter());
	}
}
