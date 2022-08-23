package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import telco.entities.Service;

@Stateless
public class ServiceService {
	
	/* 
	 * Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager.
	 */
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	public ServiceService() {}
	
	public List<Service> findAllServices() {
		try {
			return em.createNamedQuery("Service.findAllServices", Service.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Service findServiceById(int serviceId) {
		return em.find(Service.class, serviceId);
	}
	
	public Service findServiceByType(String type) {
		try {
			return em.createNamedQuery("Service.findServiceByType", Service.class).setParameter(1, type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
