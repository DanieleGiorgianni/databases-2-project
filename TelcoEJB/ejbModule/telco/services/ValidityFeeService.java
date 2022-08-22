package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import telco.entities.ValidityFee;

@Stateless
public class ValidityFeeService {
	
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em;
	
	public ValidityFeeService () {};
	
	public List<ValidityFee> findAllValidityFees() {
		try {
			return em.createNamedQuery("ValidityFee.findAllValidityFees", ValidityFee.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public ValidityFee findValidityFeeById (int validityFeeId) {
		return em.find(ValidityFee.class, validityFeeId);
	}
}
