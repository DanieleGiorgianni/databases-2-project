package telco.triggers;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name= "total_sales_per_package", schema = "telcodb")
public class TotalSalesPerPackage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int packageid;

	// Attributes.
	private int packageSoldWithProducts;
	private int packageSoldWithoutProducts;
	
	public TotalSalesPerPackage () {}

	// Getters and Setters.
	public int getPackageid() {
		return packageid;
	}

	public void setPackageid(int packageid) {
		this.packageid = packageid;
	}

	public int getPackageWithProducts() {
		return packageSoldWithProducts;
	}

	public void setPackageWithProducts(int packageWithProducts) {
		this.packageSoldWithProducts = packageWithProducts;
	}

	public int getPackageWithoutProducts() {
		return packageSoldWithoutProducts;
	}

	public void setPackageWithoutProducts(int packageWithoutProducts) {
		this.packageSoldWithoutProducts = packageWithoutProducts;
	}
}
