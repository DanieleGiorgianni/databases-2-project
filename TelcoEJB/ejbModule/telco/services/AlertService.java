package telco.services;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Alert;
import telco.entities.User;

@Stateless
public class AlertService {
	
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	public AlertService() {}
	
	public Alert findAlertById(int alertId) {
		return em.find(Alert.class, alertId);
	}

	public List<Alert> findAllAlerts() {
		return em.createNamedQuery("Alert.findAllAlerts", Alert.class).getResultList();
	}
	
	public Alert findAlertByUser(String username) { //wouldn't it be better to use userId instead of username?
		return em.createNamedQuery("Alert.findAlertByUser", Alert.class).setParameter(1, username).getSingleResult();
	}
	
	public void createAlert(User user, int amount, Timestamp lastDate) {
		Alert alert = new Alert();
		alert.setUsername(user.getUsername());
		alert.setEmail(user.getEmail());
		alert.setAmount(amount);
		alert.setLastdatetime(lastDate);
		alert.setUser(user);
		
		em.persist(alert);
		em.flush();
		
		System.out.println("AlertService: Alert correctly created !");	
	}
	
	public void deleteAlert (User user) {
		Alert a = findAlertByUser (user.getUsername());
		if (a != null) {
			em.remove(a);
			em.flush();
		}
		System.out.println ("AlertService: Alert correctly deleted !");
	}
}
