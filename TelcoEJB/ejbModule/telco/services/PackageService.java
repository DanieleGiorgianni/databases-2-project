package telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Package;

@Stateless
public class PackageService {
	
	/* Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager. */
	@PersistenceContext(unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	public PackageService() {}
	
	public List<Package> findAllPackages() {
		return em.createNamedQuery("Package.findAll", Package.class).getResultList();
	}
	
	public Package findPackageById(int packageId) {
		return em.find(Package.class, packageId);
	}
}
