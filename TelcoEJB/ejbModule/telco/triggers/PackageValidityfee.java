package telco.triggers;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class PackageValidityfee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Attributes.
	private int packageid;
	private int validityfeeid;
	
	// Getters and Setters.
	public int getPackageid() {
		return packageid;
	}
	public void setPackageid(int packageid) {
		this.packageid = packageid;
	}
	public int getValidityfeeid() {
		return validityfeeid;
	}
	public void setValidityfeeid(int validityfeeid) {
		this.validityfeeid = validityfeeid;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(packageid, validityfeeid);
	}
	//Auto-generated method
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		PackageValidityfee other = (PackageValidityfee) object;
		return packageid == other.packageid && validityfeeid == other.validityfeeid;
	}
}
