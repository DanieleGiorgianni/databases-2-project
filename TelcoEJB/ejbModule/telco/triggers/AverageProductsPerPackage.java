package telco.triggers;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "average_optional_products_per_package", schema = "telcodb")
public class AverageProductsPerPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int packageid;
	
	// Attributes.
	private int orderwithproduct;
	private int totalorder;
	private float averageproduct;
	
	public AverageProductsPerPackage () {}

	// Getters and Setters.
	public int getPackadeid() {
		return packageid;
	}

	public void setPackadeid(int packageid) {
		this.packageid = packageid;
	}
	
	public int getOrderwithproduct() {
		return orderwithproduct;
	}

	public void setOrderwithproduct(int orderwithproduct) {
		this.orderwithproduct = orderwithproduct;
	}

	public int getTotalorder() {
		return totalorder;
	}

	public void setTotalorder(int totalorder) {
		this.totalorder = totalorder;
	}

	public float getAverageproduct() {
		return averageproduct;
	}

	public void setAverageproduct(int averageproduct) {
		this.averageproduct = averageproduct;
	}
}
