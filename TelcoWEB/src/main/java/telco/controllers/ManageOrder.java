package telco.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import telco.entities.Order;
import telco.entities.Package;
import telco.entities.Product;
import telco.entities.User;
import telco.entities.ValidityFee;
import telco.services.AlertService;
import telco.services.OrderService;
import telco.services.PackageService;
import telco.services.ProductService;
import telco.services.SasService;
import telco.services.UserService;
import telco.services.ValidityFeeService;

@WebServlet ("/ManageOrder")
public class ManageOrder extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/PackageService")
	private PackageService packageService;
	
	@EJB (name = "telco.services/ValidityFeeService")
	private ValidityFeeService validityFeeService;
	
	@EJB (name = "telco.services/ProductService")
	private ProductService productService;
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;
	
	@EJB (name = "telco.services/SasService")
	private SasService sasService;
	
	@EJB (name = "telco.services/UserService")
	private UserService userService;
	
	@EJB (name = "telco.services/AlertService")
	private AlertService alertService;
	
	public ManageOrder() {
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
	  * Method that takes charge of the correct creation of the order. 
	  * In case of valid payment it sets the deactivation date and calls sas creation. 
	  * Otherwise, it still creates the order (with valid to false) 
	  * requiring the user to be set as insolvent and check alert status.
	  */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet in ManageOrder");
		
		// Values to be obtained from request.
		Integer monthlyfee = 0;
		Date startdate = null;
		User user = null;
		Integer packageId = null;
		Package pack = null;
		Integer validityfeeId = null;
		ValidityFee validityfee = null;
		String[] productsIdStrings = null;
		Integer productId = null;
		List<Product> products = new ArrayList<Product>();
		
		// Values to be determined from those obtained.
		Timestamp purchasedate = null;
		int fails = 0;
		boolean valid = false;
		
		monthlyfee = Integer.parseInt(request.getParameter("monthlyfee"));
		startdate = Date.valueOf(request.getParameter("startdate"));
		
		user = (User) request.getSession().getAttribute("user");
		
		packageId =  Integer.parseInt(request.getParameter("packageId"));
		pack = packageService.findPackageById(packageId);
		
		validityfeeId = Integer.parseInt(request.getParameter("validityfeeId"));
		validityfee = validityFeeService.findValidityFeeById(validityfeeId);
		
		productsIdStrings = request.getParameterValues("productId");
		if (productsIdStrings != null) {
			for (String productIdString : productsIdStrings) {
				productId = Integer.parseInt(productIdString);
				products.add(productService.findProductById(productId));
			}
		}
		else {
			products = null;
		}
		
		String payment = request.getParameter("payment");
		// Correct payment.
		if (payment.contains("OK")) {
			System.out.println("> Payment OK");
			purchasedate = new Timestamp(System.currentTimeMillis());
			fails = 0;
			valid = true;
			
			Order order = null;
			order = orderService.createOrder(monthlyfee, purchasedate, startdate, fails, valid, user, pack, validityfee, products);
			
			// sas creation (if payment is OK).
			Date deactivationdate = Date.valueOf(startdate.toLocalDate().plusMonths(validityfee.getMonths()));
			sasService.createSas(deactivationdate, order, user);
		}
		// Failed payment.
		else {
			System.out.println("> Payment FAIL");
			purchasedate = null;
			fails = 1;
			valid = false;
			
			Order orderFailed = null;
			orderFailed = orderService.createOrder(monthlyfee, purchasedate, startdate, fails, valid, user, pack, validityfee, products);
			
			userService.insolventManager(user);
			
			alertService.alertManager(user, new Timestamp(System.currentTimeMillis()));
		}
		
		String path = getServletContext().getContextPath() + "/GoToHome";
		response.sendRedirect(path);
	}
}
