package telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "validityfee", schema = "telcodb")

@NamedQuery (name = "ValidityFee.findAllValidityFees", query = "SELECT v FROM ValidityFee v ")
@NamedQuery (name = "ValidityFee.findValidityFeeById", query = "SELECT v FROM ValidityFee v WHERE v.id = ?1")

public class ValidityFee implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private int months;
	private int monthlyfee;
	
	public ValidityFee () {}
	
	//Relationship
	@OneToMany (mappedBy = "validityfee")
	private List<Order> orders;
	
	@ManyToMany (mappedBy = "validityfees")
	private List<Package> packages;

	//Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getMonthlyfee() {
		return monthlyfee;
	}

	public void setMonthlyfee(int monthlyfee) {
		this.monthlyfee = monthlyfee;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
}
