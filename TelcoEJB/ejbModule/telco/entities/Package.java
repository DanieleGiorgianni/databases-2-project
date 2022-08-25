package telco.entities;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "package", schema = "telcodb")

@NamedQuery (name = "Package.findAllPackages", query = "SELECT p FROM Package p")
@NamedQuery (name = "Package.findPackageByName", query = "SELECT p FROM Package p WHERE p.name = ?1")

public class Package implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	// Attributes.
	private String name;
	
	public Package() {}
	
	// Relationships.
	@OneToMany (mappedBy = "pack")
	private List<Order> orders = new ArrayList<Order>();
	
	@ManyToOne
	@JoinColumn (name = "employeeid") 
	private Employee employee;
	
	@ManyToMany
	@JoinTable (name = "package_includes_service", joinColumns = @JoinColumn(name = "packageid"), 
	inverseJoinColumns = @JoinColumn (name = "serviceid"))
	private List<Service> services = new ArrayList<Service>();
	
	@ManyToMany
	@JoinTable (name = "package_contains_product", joinColumns = @JoinColumn (name = "packageid"), 
	inverseJoinColumns = @JoinColumn (name = "productid"))
	private List<Product> products = new ArrayList<Product>();
	
	@ManyToMany
	@JoinTable (name = "package_offers_validityfee", joinColumns = @JoinColumn (name = "packageid"),
	inverseJoinColumns = @JoinColumn (name = "validityfeeid"))
	private List<ValidityFee> validityfees = new ArrayList<ValidityFee>(); 
	
	// Getters and Setters.
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
	
	public List<ValidityFee> getValidityFees() {
		return validityfees;
	}
	
	public void setValidityFees(List<ValidityFee> validityfees) {
		this.validityfees = validityfees;
	}
}
