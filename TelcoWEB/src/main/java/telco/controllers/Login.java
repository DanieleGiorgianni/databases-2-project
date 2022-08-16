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

import telco.entities.User;
import telco.exceptions.CredentialsException;
import telco.services.UserService;

@WebServlet("/Login")
public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "telco.services/UserService")
	private UserService userService;

	public Login() {
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = null;
		String password = null;
		String path;
		String string = "Login fails";
		User user = null;
		
		System.out.println("doPost in Login");
		
		username = StringEscapeUtils.escapeJava(request.getParameter("username"));
		password = StringEscapeUtils.escapeJava(request.getParameter("password"));
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		// Check if parameters are present
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			ctx.setVariable("loginMsg", "Incorrect username or password");
			path = "/WEB-INF/login.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
		
		try {
			// Obtaining a (possible) user given specified username and password
			user = userService.login(username, password);
			if (user != null) {
				request.getSession().setAttribute("user", user);
				
				path = getServletContext().getContextPath() + "/GoToHome";
				response.sendRedirect(path);
				
				return;
			}
		} catch (NonUniqueResultException | CredentialsException e) {
			e.printStackTrace();
		}
		
		System.out.println("> Something went wrong [" + string + "]");
		ctx.setVariable("loginMsg", "Something went wrong [" + string + "]");
		path = "/WEB-INF/login.html";
		templateEngine.process(path, ctx, response.getWriter());
	}
}
