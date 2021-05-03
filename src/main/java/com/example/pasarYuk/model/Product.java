package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "product")
public class Product implements Comparable<Product> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	
	@Column(name = "sellerId")
	private long sellerId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_desc")
	private String productDesc;
	
	@Column(name = "price")
	private long price;
	
	@Column(name = "satuan_jual")
	private String satuanJual;
	
	@Column(name = "avg_star")
	private float avgStar;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "url_product_image")
	private String urlProductImage;

	public Product() {
		super();
	}

	public Product(String urlProductImage, long sellerId, String productName, String productDesc, long price, float avgStar, String category, String satuanJual, long productId) {
		super();
		this.productId = productId;
		this.sellerId = sellerId;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
		this.avgStar = avgStar;
		this.category = category;
		this.satuanJual = satuanJual;
		this.urlProductImage = urlProductImage;
	}
	
	public Product(String urlProductImage, long sellerId, String productName, String productDesc, long price, float avgStar, String category, String satuanJual) {
		super();
		this.sellerId = sellerId;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
		this.avgStar = avgStar;
		this.category = category;
		this.satuanJual = satuanJual;
		this.urlProductImage = urlProductImage;
	}

	public Object clone() {
		Product clone = new Product(this.urlProductImage, this.sellerId, this.productName, this.productDesc, this.price, this.avgStar, this.category, this.satuanJual, this.productId);
		return clone;
	}
	
	public String getUrlProductImage() {
		return urlProductImage;
	}

	public void setUrlProductImage(String urlProductImage) {
		this.urlProductImage = urlProductImage;
	}

	public String getSatuanJual() {
		return satuanJual;
	}

	public void setSatuanJual(String satuanJual) {
		this.satuanJual = satuanJual;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long lapakId) {
		this.sellerId = lapakId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public float getAvgStar() {
		return avgStar;
	}

	public void setAvgStar(float avgStar) {
		this.avgStar = avgStar;
	}

	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
