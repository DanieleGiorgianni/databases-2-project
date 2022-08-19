package telco.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import telco.entities.Package;
import telco.entities.Product;
import telco.entities.Service;
import telco.entities.ValidityFee;

@Stateless
public class PackageService {
	
	/* Persistence Context is a set of objects that need to be managed 
	 * and tracked in their changes by the Entity Manager. */
	@PersistenceContext (unitName = "TelcoEJB")
	private EntityManager em; // Interface for interacting with a Persistence Context.
	
	@EJB(name = "telco.services/ServiceService")
	private ServiceService serviceService;
	
	@EJB(name = "telco.services/ValidityFeeService")
	private ValidityFeeService validityFeeService;
	
	@EJB(name = "telco.services/ProductService")
	private ProductService productService;
	
	public PackageService() {}
	
	public List<Package> findAllPackages() {
		return em.createNamedQuery("Package.findAllPackages", Package.class).getResultList();
	}
	
	public Package findPackageById(int packageId) {
		return em.find(Package.class, packageId);
	}
	
	public Package findPackageByName(String name) {
		return em.createNamedQuery("Package.findPackageByName", Package.class).setParameter(1, name).getSingleResult();
	}
	
	public List<Product> findProductsByPackageId (int packageId) {
		return em.find(Package.class, packageId).getProducts();
	}
	
	public List<ValidityFee> findValidityFeesByPackageId (int packageId) {
		return em.find(Package.class, packageId).getValidityFees();
	}
	
	public String createPackage(String name, String[] validity, String[] services, String[] products) {
		//Check if already exists a package with the same name
		List<Package> packs = findAllPackages();
		for (Package p : packs) {
			if (p.getName().equals(name))
				return "A package with the same name already exists";
		}
		
		if (services.length == 0)
			return "Select at least one service in order to create a package ";
		if (validity.length == 0)
			return "Select at least one validity for the new package ";
		
		Package pack = new Package();
		pack.setName(name);
		
		List<Service> addServices = new ArrayList<Service> ();
		for (String s : services) {
			Integer sId = Integer.parseInt(s);
			Service addService = serviceService.findServiceById(sId);
			addServices.add(addService);
		}
		pack.setServices(addServices);
		
		List<ValidityFee> addValidityFees = new ArrayList<ValidityFee> ();
		for (String v : validity) {
			Integer vId = Integer.parseInt(v);
			ValidityFee addValidityFee = validityFeeService.findValidityFeeById(vId);
			addValidityFees.add(addValidityFee);
		}
		pack.setValidityFees(addValidityFees);
		
		if (products != null) {
			List<Product> addProducts = new ArrayList<Product> ();
			for (String p : products) {
				Integer pId = Integer.parseInt(p);
				Product addProduct = productService.findProductById(pId);
				addProducts.add(addProduct);
			}
		pack.setProducts(addProducts);
		}
		
		em.persist(pack);
		em.flush();
		return "OK";
	}
}
