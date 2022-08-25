package telco.triggers;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "optional_product_best_seller", schema = "telcodb")
public class ProductBestSeller implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int productid;
	
	// Attributes.
	private int productsales;
	
	public ProductBestSeller () {}

	// Getters and Setters.
	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getProductsales() {
		return productsales;
	}

	public void setProductsales(int productsales) {
		this.productsales = productsales;
	}
}
