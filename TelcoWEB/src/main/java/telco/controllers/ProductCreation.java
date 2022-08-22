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
import telco.services.ProductService;

@WebServlet ("/ProductCreation")
public class ProductCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB (name = "telco.services/ProductService")
	private ProductService productService;
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("productname");
		Integer monthlyfee = Integer.parseInt(request.getParameter("productmonthlyfee"));
		String path;
		String string;
		
		System.out.println("doPost in ProductCreation");
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Check if parameters are present.
		if (name == null || monthlyfee <= 0) {
			string = "Incorrect name or monthlyfee";
			ctx.setVariable("productMsg", string);
		}
		else {
			Employee employee = (Employee) request.getSession().getAttribute("employee");
			
			string = productService.createProduct(name, monthlyfee, employee.getId());
			if (string.equals("OK")){
				ctx.setVariable("productMsg", "Product created successfully !");
				
				path = getServletContext().getContextPath() + "/GoToEmployeeHome";
				response.sendRedirect(path);
				
				return;
			}
		}
		
		System.out.println ("> Something went wrong [" + string + "]");
		ctx.setVariable("productMsg", "Something went wrong [" + string + "]");
		path = "/WEB-INF/employee-home.html";
		templateEngine.process(path, ctx, response.getWriter());
	}
}
