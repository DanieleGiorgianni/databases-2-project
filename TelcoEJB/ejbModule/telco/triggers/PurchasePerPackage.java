package telco.triggers;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "purchase_per_package", schema = "telcodb")

public class PurchasePerPackage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int packageid;

	// Attributes.
	private int packagepurchases;
	
	public PurchasePerPackage() {}

	// Getters and Setters.
	public int getPackageid() {
		return packageid;
	}

	public void setPackadeid(int packageid) {
		this.packageid = packageid;
	}

	public int getPackagepurchases() {
		return packagepurchases;
	}

	public void setPackagepurchases(int packagepurchases) {
		this.packagepurchases = packagepurchases;
	
	}
}
