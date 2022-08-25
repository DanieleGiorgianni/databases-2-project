package telco.triggers;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TriggersService {
	
	@PersistenceContext
	private EntityManager em;
	
	public TriggersService() {}
	
	@SuppressWarnings("unchecked")
	public List<PurchasePerPackage> findAllPurchasePerPackage() {
		String query = "SELECT * FROM purchase_per_package p";
		return em.createNativeQuery(query, PurchasePerPackage.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<PurchasePerPackageAndValidityPeriod> findAllPurchasePerPackageAndValidityPeriod() {
		String query = "SELECT * FROM purchase_per_package_and_validityperiod pv";
		return em.createNativeQuery(query, PurchasePerPackageAndValidityPeriod.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TotalSalesPerPackage> findAllTotalSalesPerPackage() {
		String query = "SELECT * FROM total_sales_per_package t";
		return em.createNativeQuery(query, TotalSalesPerPackage.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<AverageProductsPerPackage> findAllAverageProductsPerPackage() {
		String query = "SELECT * FROM average_optional_products_per_package ao";
		return em.createNativeQuery(query, AverageProductsPerPackage.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductBestSeller> findAllProductBestSeller() {
		String query = "SELECT * FROM optional_product_best_seller op";
		return em.createNativeQuery(query, ProductBestSeller.class).getResultList();
	}
}
