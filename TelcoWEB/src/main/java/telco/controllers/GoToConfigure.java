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

import telco.entities.Package;
import telco.entities.Product;
import telco.entities.ValidityFee;
import telco.services.PackageService;

@WebServlet ("/GoToConfigure")
public class GoToConfigure extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/PackageService")
	private PackageService packageService;

	public GoToConfigure() {
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
	 * Once a package has been selected by the user, the method will take care of offering the configuration of it 
	 * (validity, optional products and the activation date) via the related page.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet in GoToConfigure");
		
		String path = "/WEB-INF/configure.html";
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		Integer packageId = null;
		Package pack = null;
		List<Product> products = new ArrayList<Product>();
		List<ValidityFee> validityFees = new ArrayList<ValidityFee>();
		
		packageId = Integer.parseInt(request.getParameter("packageId"));
		pack = packageService.findPackageById(packageId);
		products = packageService.findProductsByPackageId(packageId);
		validityFees = packageService.findValidityFeesByPackageId(packageId);
		
		if (packageId != null && pack != null && validityFees != null) { // No check on products because a package can have no (optional) products.
			ctx.setVariable("package", pack);
			ctx.setVariable("products", products);
			ctx.setVariable("validityfees", validityFees);
		}
		
		templateEngine.process(path, ctx, response.getWriter());
	}
}
