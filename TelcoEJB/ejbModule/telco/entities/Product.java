package telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (name = "product", schema = "telcodb")

@NamedQuery (name = "Product.findAllProducts", query = "SELECT p FROM Product p")

@NamedQuery (name = "Product.findProductById", query = "SELECT p FROM Product p WHERE p.id = ?1")
@NamedQuery (name = "Product.findProductByName", query = "SELECT p FROM Product p WHERE p.name = ?1")

public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private String name;
	private int monthlyfee;
	
	public Product() {}
	
	//Relationship
	@ManyToOne 
	@JoinColumn (name = "employeeid") //attribute that must be add in the package table
	private Employee employee;
	
	@ManyToMany (mappedBy = "products")
	private List<Package> packages;
	
	@ManyToMany (mappedBy = "products")
	private List<Order> orders;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMonthlyfee() {
		return monthlyfee;
	}
	public void setMonthlyfee(int monthlyfee) {
		this.monthlyfee = monthlyfee;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public List<Package> getPackages() {
		return packages;
	}
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
