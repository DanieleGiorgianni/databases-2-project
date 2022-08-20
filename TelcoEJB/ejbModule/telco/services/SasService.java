package telco.services;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
		return em.createNamedQuery("SasService.findAllAlerts", Sas.class).getResultList();	
	}
	
	public Sas findSasById(int sasId) {
		return em.find(Sas.class, sasId);
	}
	
	public List<Sas> findSasByUser(String username) {  //wouldn't it be better to use userId instead of username?
		return em.createNamedQuery("Sas.findSasByUser", Sas.class).setParameter(1, username).getResultList();
	}
	
	public List<Sas> findSasByOrder(int orderid) {
		return em.createNamedQuery("Sas.findSasByOrder", Sas.class).setParameter(1, orderid).getResultList();
	}
	
	public void createSas(Date deactivationDate, Order order, User user) {
		Sas sas = new Sas ();
		sas.setDeactivationdate(deactivationDate);
		sas.setOrder(order);
		sas.setUser(user);
		em.persist(sas);
		em.flush();
		
		System.out.println ("SasService: Sas correctly created");
	}

}
