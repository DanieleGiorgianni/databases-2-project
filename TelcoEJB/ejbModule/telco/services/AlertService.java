package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Alert;

@Stateless
public class AlertService {
	
	@PersistenceContext(unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Pers
	
	public AlertService() {}
	
	public Alert findAlertById(int alertId) {
		return em.find(Alert.class,alertId);
	}

	public List<Alert> findAllAlerts() {
		return em.createNamedQuery("Alert.findAllAlerts", Alert.class).getResultList();
	}
	
	public List<Alert> findAlertByUser() {
		return em.createNamedQuery("Alert.findAlertByUser", Alert.class).getResultList();
	}
}
