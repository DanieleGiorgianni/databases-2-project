package telco.entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Table;

@Entity
@Table (name="order", schema ="telcodb")

@NamedQuery (name = "Order.findAllOrders", query = "SELECT o FROM Order o")
@NamedQuery (name = "Order.findOrderById", query = "SELECT o FROM Order o WHERE o.id = ?1")

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private int monthlyfee;
	private Timestamp purchasedate;
	private int validity;
	private int fails;
	
	public Order() {}
	
	//Relationship
	@ManyToOne
	@JoinColumn (name = "userid")  //need to add field "userid" in order table as foreign key
	private User user; 
	
	@ManyToOne 
	@JoinColumn (name = "packageid")
	private Package pack;
	
	@ManyToMany
	@JoinTable (name = "order_comprises_product", joinColumns = @JoinColumn (name = "orderid"),
	inverseJoinColumns = @JoinColumn (name = "productid"))
	private List<Product> products;

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

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}	
}
