package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Product;

@Stateless
public class ProductService {
	
	/* Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager. */
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	public ProductService() {}
	
	public List<Product> findAllProducts(){
		return em.createNamedQuery("Product.findAllProducts", Product.class).getResultList();
	}
	
	public Product findProductById(int productId) {
		return em.find(Product.class, productId);
	}
	
	public Product findProductByName(String name){
		return em.createNamedQuery("Product.findProductByName", Product.class).setParameter(1, name).getSingleResult();
	}
	
	public String createProduct(String name, int monthlyfee) {
		List<Product> products = findAllProducts();
		for (Product p : products) {
			if (p.getName().equals(name))
				return "A product with the same name already exists";
		}
		
		Product product = new Product();
		product.setName(name);
		product.setMonthlyfee(monthlyfee);
		
		em.persist(product);
		em.flush();
		return "OK";
	}
}
