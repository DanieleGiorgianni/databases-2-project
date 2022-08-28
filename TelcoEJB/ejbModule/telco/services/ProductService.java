package telco.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import telco.entities.Employee;
import telco.entities.Product;

@Stateless
public class ProductService {
	
	/* 
	 * Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager.
	 */
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	@EJB(name = "telco.services/EmployeeService")
	private EmployeeService employeeService;
	
	public ProductService() {}
	
	public List<Product> findAllProducts(){
		try {
			return em.createNamedQuery("Product.findAllProducts", Product.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Product findProductById(int productId) {
		return em.find(Product.class, productId);
	}
	
	public Product findProductByName(String name){
		return em.createNamedQuery("Product.findProductByName", Product.class).setParameter(1, name).getSingleResult();
	}
	
	/*
	 * Method for creating an optional product.
	 */
	public String createProduct(String name, int monthlyfee, int employeeId) {
		// Check if already exists a product with the same name.
		List<Product> products = new ArrayList<Product>();
		products = findAllProducts();
		for (Product p : products) {
			if (p.getName().equals(name))
				return "A product with the same name already exists";
		}
		
		// Employee who is creating the package.
		Employee employee = employeeService.findEmployeeById(employeeId);
		
		// Creation of the new product.
		Product product = new Product();
		product.setName(name);
		product.setMonthlyfee(monthlyfee);
		product.setEmployee(employee);
		
		em.persist(product);
		em.flush();
		return "OK";
	}
}
