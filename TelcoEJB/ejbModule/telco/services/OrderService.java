package telco.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Order;
import telco.entities.Package;
import telco.entities.Product;
import telco.entities.User;
import telco.entities.ValidityFee;

@Stateful
public class OrderService {

	/*
	 * Persistence Context is a set of objects that need to be managed and tracked
	 * in their changes by the Entity Manager.
	 */
	@PersistenceContext(unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.

	public OrderService() {
	}

	public Order findOrderById(int orderId) {
		return em.find(Order.class, orderId);
	}

	public List<Order> findAllOrders() {
		return em.createNamedQuery("Order.findAllOrders", Order.class).getResultList();
	}

	public List<Order> findRejectedOrders() {
		return em.createNamedQuery("Order.findRejectedOrders", Order.class).getResultList();
	}

	public Order createOrder(int monthlyfee, Timestamp purchasedate, Date startdate, int fails, boolean valid,
			User user, Package pack, ValidityFee validityfee, List<Product> products) {
		Order order = new Order();
		order.setMonthlyfee(monthlyfee);
		order.setPurchasedate(purchasedate);
		order.setStartdate(startdate);
		order.setFails(fails);
		order.setValid(valid);
		order.setUser(user);
		order.setPack(pack);
		order.setValidityfee(validityfee);
		order.setProducts(products);
		
		em.persist(order);
		em.flush();
		System.out.println("createOrder in OrderService DONE");
		return order;
	}
	
	public void fixOrder(Order fixOrder) {
		Order order = findOrderById(fixOrder.getId());
		order.setPurchasedate(fixOrder.getPurchasedate());
		order.setFails(fixOrder.getFails());
		order.setValid(fixOrder.isValid());
		
		em.persist(order);
		em.flush();
		System.out.println("fixOrder in OrderService DONE");
	}
}
