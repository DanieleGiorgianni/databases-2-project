package telco.services;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Order;

@Stateful
public class OrderService {
	
	/* Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager. */
	@PersistenceContext(unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.

	public OrderService() {}
	
	public List<Order> findAllOrders() {
		return em.createNamedQuery("Order.findAllOrders", Order.class).getResultList();
	}
	
	public Order findOrderById(int orderId) {
		return em.find(Order.class, orderId);
	}
}
