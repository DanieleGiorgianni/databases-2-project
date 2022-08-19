package telco.controllers;

import java.io.IOException;
import java.sql.Date;
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

import telco.entities.Package;
import telco.entities.Product;
import telco.entities.ValidityFee;
import telco.services.PackageService;
import telco.services.ProductService;
import telco.services.ValidityFeeService;

@WebServlet ("/GoToBuy")
public class GoToBuy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/PackageService")
	private PackageService packageService;
	
	@EJB (name = "telco.services/ProductService")
	private ProductService productService;
	
	@EJB (name = "telco.services/ValidityFeeService")
	private ValidityFeeService validityFeeService;
	
	public GoToBuy() {
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
		System.out.println("doGet in GoToBuy");
		
		String path = "/WEB-INF/buy.html";
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		Integer packageId = null;
		Package pack = null;
		String[] productsIdStrings = null;
		Integer productId = null;
		List<Product> products = new ArrayList<Product>();
		ValidityFee validityFee = null;
		Date startDate = null;
		int monthlyfee = 0;
		int totalfee = 0;
		
		packageId = Integer.parseInt(request.getParameter("packageId"));
		pack = packageService.findPackageById(packageId);
		productsIdStrings = request.getParameterValues("productId");
		if (productsIdStrings != null) {
			for (String productIdString : productsIdStrings) {
				productId = Integer.parseInt(productIdString);
				products.add(productService.findProductById(productId));
			}
		}
		validityFee = validityFeeService.findValidityFeeById(Integer.parseInt(request.getParameter("validityfeeId")));
		startDate = Date.valueOf(request.getParameter("startdate"));
		
		// Total monthly cost calculation
		monthlyfee += validityFee.getMonthlyfee();
		if (!products.isEmpty()) {
			for (Product p : products)
				monthlyfee += p.getMonthlyfee();
		}
		
		totalfee = monthlyfee*validityFee.getMonths();
		
		if (packageId != null && pack != null && validityFee != null) {
			ctx.setVariable("package", pack);
			ctx.setVariable("products", products);
			ctx.setVariable("validityfee", validityFee);
			ctx.setVariable("startdate", startDate);
			ctx.setVariable("monthlyfee", monthlyfee);
			ctx.setVariable("totalfee", totalfee);
		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}
}
