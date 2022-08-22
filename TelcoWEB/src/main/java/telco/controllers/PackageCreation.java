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

import telco.entities.Employee;
import telco.services.PackageService;


@WebServlet ("/PackageCreation")
public class PackageCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB (name = "telco.services/PackageService")
	private PackageService packageService;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("packagename");	
		String[] validityFees = request.getParameterValues("packagevalidity");
		String[] services = request.getParameterValues("packageservice");
		String[] products = request.getParameterValues("packageproducts");
		String path;
		String string;
		
		System.out.println("doPost in PackageService");
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Check if parameters are present.
		if (name == null || validityFees == null || services == null) {
			string = "Incorret name or validityFee or service";
			ctx.setVariable("packageMsg", string);
			
		}
		else {
			Employee employee = (Employee) request.getSession().getAttribute("employee");
			
			string = packageService.createPackage(name, employee.getId(), services, products, validityFees);
			if (string.equals("OK")) {
				ctx.setVariable("packageMsg", "Package created successfully !");
				
				path = getServletContext().getContextPath() + "/GoToEmployeeHome";
				response.sendRedirect(path);
				
				return;
			}
		}
		
		System.out.println ("> Something went wrong [" + string + "]");
		ctx.setVariable("packageMsg", "Something went wrong [" + string + "]");
		path = "/WEB-INF/employee-home.html";
		templateEngine.process(path, ctx, response.getWriter());
	}
}
