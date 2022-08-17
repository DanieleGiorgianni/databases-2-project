package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Service;

@Stateless
public class ServiceService {
	
	/* Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager. */
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	public ServiceService() {}
	
	public List<Service> findAllServices() {
		return em.createNamedQuery("Service.findAllServices", Service.class).getResultList();
	}
	
	public Service findServiceById(int serviceId) {
		return em.find(Service.class, serviceId);
	}
	
	public List<Service> findServiceByType() {
		return em.createNamedQuery("Service.findServiceByType", Service.class).getResultList();
	}
}
