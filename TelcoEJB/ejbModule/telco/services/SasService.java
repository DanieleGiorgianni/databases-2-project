package telco.services;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import telco.entities.Order;
import telco.entities.Sas;
import telco.entities.User;

@Stateless
public class SasService {
	
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em;
	
	public SasService () {}

	public List<Sas> findAllSas() {
		try {
			return em.createNamedQuery("SasService.findAllSas", Sas.class).getResultList();	
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Sas findSasById(int sasId) {
		return em.find(Sas.class, sasId);
	}
	
	public List<Sas> findSasByUser(User user) {
		try {
			return em.createNamedQuery("Sas.findSasByUser", Sas.class).setParameter(1, user).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Sas> findSasByOrder(Order order) {
		try {
			return em.createNamedQuery("Sas.findSasByOrder", Sas.class).setParameter(1, order).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/*
	 * Method for creating a service activation schedule given an order, its deactivation date and its related user.
	 */
	public void createSas(Date deactivationDate, Order order, User user) {
		Sas sas = new Sas();
		sas.setDeactivationdate(deactivationDate);
		sas.setOrder(order);
		sas.setUser(user);
		
		em.persist(sas);
		em.flush();
		System.out.println ("createSas in SasService DONE");
	}
}
