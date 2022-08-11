package telco.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import telco.exceptions.CredentialsException;
import telco.services.UserService;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	
	@EJB(name = "telco.services/UserService") // Creates a reference in component namespace
	private UserService userService;

	public Registration() {
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
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = null;
		String password = null;
		String email = null;
		String path;
		String string = "Registration fails";
		
		System.out.println("doPost in Registration");
			
		username = StringEscapeUtils.escapeJava(request.getParameter("username"));
		email = StringEscapeUtils.escapeJava(request.getParameter("email"));
		password = StringEscapeUtils.escapeJava(request.getParameter("password"));
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		try {
			string = userService.registration(username, email, password);
			if (string == "OK") {
				// Sets registrationMsg variable in registration.html to the following message.
				ctx.setVariable("registrationMsg", "Registration OK");
				// "path" is the template name.
				path = "/WEB-INF/confirmation.html";
				templateEngine.process(path, ctx, response.getWriter());
				return;
			}	
		} catch (NonUniqueResultException | CredentialsException e) {
			e.printStackTrace();
		}
		
		System.out.println("Something went wrong [" + string + "]");
		ctx.setVariable("registrationMsg", "Something went wrong [" + string + "]");
		path = "/WEB-INF/registration.html";
		templateEngine.process(path, ctx, response.getWriter());
	}
}
