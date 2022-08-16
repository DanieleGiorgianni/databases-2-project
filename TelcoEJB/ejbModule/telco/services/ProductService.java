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
	@PersistenceContext(unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	public ProductService() {}
	
	public List<Product> findAllProducts(){
		return em.createNamedQuery("Product.findAll", Product.class).getResultList();
	}
	
	public Product findProductById(int productId) {
		return em.find(Product.class, productId);
	}
}
