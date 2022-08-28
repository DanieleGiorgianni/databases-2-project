package telco.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import telco.entities.Alert;
import telco.entities.Order;
import telco.entities.User;

@Stateless
public class AlertService {
	
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	@EJB (name = "telco.services/OrderService")
	private OrderService orderService;
	
	public AlertService() {}
	
	public Alert findAlertById(int alertId) {
		return em.find(Alert.class, alertId);
	}

	public List<Alert> findAllAlerts() {
		try {
			return em.createNamedQuery("Alert.findAllAlerts", Alert.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Alert findAlertByUser(User user) {
		try {
			return em.createNamedQuery("Alert.findAlertByUser", Alert.class).setParameter(1, user).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/*
	 * Method to create an alert over a user if he/she fails 3 payments.
	 * An alert includes the total amount that needs to be paid and the date of the last failed payment.
	 */
	public void createAlert(User user, int amount, Timestamp lastDate) {
		Alert alert = new Alert();
		alert.setAmount(amount);
		alert.setLastdatetime(lastDate);
		alert.setUser(user);
		
		em.persist(alert);
		em.flush();
		System.out.println("createAlert in AlertService DONE");
	}
	
	/*
	 * Method to update the total amount to be paid and the last failed payment of an alert given a user.
	 * In this case the number of fails of a user is >= 3 (so, the user already has an alert).
	 */
	public void updateAlert(User user, int amount, Timestamp lastDate) {
		Alert alert = findAlertByUser(user);
		alert.setAmount(amount);
		alert.setLastdatetime(lastDate);
		
		em.persist(alert);
		em.flush();
		System.out.println("updateAlert in AlertService DONE");
	}
	
	/*
	 * Method to delete a user alert.
	 */
	public void deleteAlert(User user) {
		Alert a = findAlertByUser(user);
		
		if (a != null) {
			em.remove(a);
			em.flush();
		}
		System.out.println ("deleteAlert in AlertService DONE");
	}
	
	/*
	 * Method invoked in case of failed order payment.
	 * Responsible for handling an alert given a user, from creation to deletion.
	 */
	public void alertManager(User user, Timestamp lastDate) {		
		List<Order>	rejectedOrders = new ArrayList<Order>();
		rejectedOrders = orderService.findRejectedOrdersByUser(user);
		
		int failsTot = 0;
		int amount = 0;
		
		// Calculation of number of failed orders and their amount.
		for (Order rejectedOrder : rejectedOrders) {
			if (!rejectedOrder.isValid()) {
				failsTot += rejectedOrder.getFails();
				amount += (rejectedOrder.getMonthlyfee() * rejectedOrder.getValidityfee().getMonths());
			}
		}
		
		// If the number of failed payments is greater than 3
		if (failsTot >= 3) {
			// and the user does not already have an active alert.
			if (findAlertByUser(user) == null) {
				createAlert(user, amount, lastDate);
			}
			// and the user already have an active alert, it's update.
			else {
				updateAlert(user, amount, lastDate);
			}
			
		}
		// If the number of failed payments is lower than 3
		else {
			// and the user already have an active alert, it's delete.
			if (findAlertByUser(user) != null) {
				deleteAlert(user);
			}
		}
		System.out.println ("alertManager in AlertService DONE");
	}
}
