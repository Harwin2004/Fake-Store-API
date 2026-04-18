package PojoClasses;

import java.util.Arrays;

public class Cart {
	private int id;
	private int userID;
	private Product[] products;
	
	
	public Cart(int id, int userID, Product[] products) {
		super();
		this.id = id;
		this.userID = userID;
		this.products = products;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public Product[] getProducts() {
		return products;
	}


	public void setProducts(Product[] products) {
		this.products = products;
	}


	@Override
	public String toString() {
		return "Cart [id=" + id + ", userID=" + userID + ", products=" + Arrays.toString(products) + "]";
	}
	
	
	
}
