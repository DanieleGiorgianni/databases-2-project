package telco.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name="order", schema ="telcodb")

@NamedQuery (name = "Order.findAllOrders", query = "SELECT o FROM Order o")
@NamedQuery (name = "Order.findOrderById", query = "SELECT o FROM Order o WHERE o.id = ?1")

@NamedQuery (name = "Order.findRejectedOrders", query = "SELECT o FROM Order o WHERE o.valid = TRUE")

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private int monthlyfee;
	private Timestamp purchasedate;
	private Date startdate;
	//private int validityperiod; whit validityfeeid is useless
	private int fails;
	private boolean valid;
	
	public Order() {}
	
	//Relationship
	@ManyToOne
	@JoinColumn (name = "userid")  //need to add field "userid" in order table as foreign key
	private User user;
	
	@ManyToOne 
	@JoinColumn (name = "packageid")
	private Package pack;
	
	@ManyToOne
	@JoinColumn (name = "validityfeeid")
	private ValidityFee validityfee;
	
	@ManyToMany
	@JoinTable (name = "order_comprises_product", joinColumns = @JoinColumn (name = "orderid"),
	inverseJoinColumns = @JoinColumn (name = "productid"))
	private List<Product> products = new ArrayList<Product>();
	
	@OneToOne (mappedBy = "order")
	private Sas sas;

	//Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMonthlyfee() {
		return monthlyfee;
	}

	public void setMonthlyfee(int monthlyfee) {
		this.monthlyfee = monthlyfee;
	}

	public Timestamp getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(Timestamp purchasedate) {
		this.purchasedate = purchasedate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public int getFails() {
		return fails;
	}

	public void setFails(int fails) {
		this.fails = fails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Package getPack() {
		return pack;
	}

	public void setPack(Package pack) {
		this.pack = pack;
	}
	
	public ValidityFee getValidityfee() {
		return validityfee;
	}

	public void setValidityfee(ValidityFee validityfee) {
		this.validityfee = validityfee;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public Sas getSas() {
		return sas;
	}

	public void setSas(Sas sas) {
		this.sas = sas;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

//	public int getValidityperiod() {
//		return validityperiod;
//	}
//
//	public void setValidityperiod(int validityperiod) {
//		this.validityperiod = validityperiod;
//	}	
}
