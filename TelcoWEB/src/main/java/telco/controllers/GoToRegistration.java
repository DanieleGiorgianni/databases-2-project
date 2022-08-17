package telco.controllers;

import java.io.IOException;

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

@WebServlet ("/GoToRegistration") // Servlet's URL
public class GoToRegistration extends HttpServlet {
	
	/* Universal version identifier for Serializable class.
	 * Deserialization uses this number to ensure that a 
	 * loaded class corresponds exactly to a serializable object.
	 * If no match is found, then an InvalidClassException is thrown.*/
	private static final long serialVersionUID = 1L;
	
	/* The Template Engine allows HTML pages to be displayed 
	 * by interpreting Thymeleaf's tags. */
	private TemplateEngine templateEngine;

	public GoToRegistration() {
		super();
	}

	// Servlet initialization
	public void init() throws ServletException {
		
		/* Obtaining the context path of the web application.
		 * Each servlet of the same web application share a unique ServletContext,
		 * and this method is used to obtain its own. */
		ServletContext servletContext = getServletContext();
		
		/* ServletContextTemplateResolver resolves templates 
		 * with provided prefix and suffix and other settings*/
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		
		// Sets the template mode to be applied to templates resolved by this resolver.
		templateResolver.setTemplateMode(TemplateMode.HTML);
		
		this.templateEngine = new TemplateEngine();
		
		// Sets a single template resolver for this template engine.
		this.templateEngine.setTemplateResolver(templateResolver);
		
		/* Sets a new (optional) suffix to be added to all template names 
		 * in order to convert template names into resource names. */
		templateResolver.setSuffix(".html");
	}

	// Handles GET request
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet in GoToRegistration");
		
		String path = "/WEB-INF/registration.html";
		
		ServletContext servletContext = getServletContext();
		
		/* WebContext, a web-specific implementation extending the IWebContext subinterface, offering 
		 * access to request, session and servletcontext (application) attributes in special expression objects.
		 * Using an implementation of IWebContext is required when using Thymeleaf for generating 
		 * HTML interfaces in web applications based on the Servlet API. */
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		/* In order to execute templates, the different process(...) methods should be used.
		 * Those are mostly divided into two blocks: those that return the template processing result as a String,
		 * and those that receive a Writer as an argument and use it for writing the result instead.
		 * By specifying a writer, we can avoid the creation of a String containing the whole processing result
		 * by writing this result into the output stream as soon as it is produced from the processed template. 
		 * "path" is the template name.*/
		templateEngine.process(path, ctx, response.getWriter());
	}
}