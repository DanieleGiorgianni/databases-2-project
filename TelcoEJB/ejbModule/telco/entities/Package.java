package telco.entities;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "package", schema = "telcodb")

@NamedQuery (name = "Package.findAllPackages", query = "SELECT p FROM Package p")

@NamedQuery (name = "Package.findPackageById", query = "SELECT p FROM Package p WHERE p.id = ?1")
@NamedQuery (name = "Package.findPackageByName", query = "SELECT p FROM Package p WHERE p.name = ?1")

public class Package implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//Attributes
	private String name;
	private int validity;
	private int monthlyfee;
	
	public Package() {}
	
	//Relationship
	@OneToMany (mappedBy = "pack")
	private List<Order> orders;
	
	@ManyToOne
	@JoinColumn (name = "employeeid") 
	private Employee employee;
	
	@ManyToMany
	@JoinTable (name = "package_includes_service", joinColumns = @JoinColumn(name = "packageid"), 
	inverseJoinColumns = @JoinColumn (name = "serviceid"))
	private List<Service> services;
	
	@ManyToMany
	@JoinTable (name = "package_contains_product", joinColumns = @JoinColumn (name = "packageid"), 
	inverseJoinColumns = @JoinColumn (name = "productid"))
	private List<Product> products;
	
	@ManyToMany
	@JoinTable (name = "package_offers_validityfee", joinColumns = @JoinColumn (name = "packageid"),
	inverseJoinColumns = @JoinColumn (name = "validityfeeid"))
	private List<ValidityFee> validityfee; 
	
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
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<ValidityFee> getValidityfee() {
		return validityfee;
	}
	public void setValidityfee(List<ValidityFee> validityfee) {
		this.validityfee = validityfee;
	}
}
