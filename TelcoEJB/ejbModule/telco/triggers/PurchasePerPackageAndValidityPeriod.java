package telco.triggers;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "purchase_per_package_and_validityperiod", schema = "telcodb")

public class PurchasePerPackageAndValidityPeriod implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverride ( name = "packageid", column = @Column( name = "packageid"))
	private PackageValidityfee pvid;

	// Attributes.
	private int packagepurchases;
	private int validityfeemonths;
	
	public PurchasePerPackageAndValidityPeriod() {}

	// Getters and Setters.
	public PackageValidityfee getPvid() {
		return pvid;
	}

	public void setPvid(PackageValidityfee pvid) {
		this.pvid = pvid;
	}

	public int getPackagepurchases() {
		return packagepurchases;
	}

	public void setPackagepurchases(int packagepurchases) {
		this.packagepurchases = packagepurchases;
	}

	public int getValidityfeemonths() {
		return validityfeemonths;
	}

	public void setValidityfeemonths(int validityfeemonths) {
		this.validityfeemonths = validityfeemonths;
	}

	
}
