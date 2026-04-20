package PojoClasses;

import java.util.Arrays;

public class Cart {
	private String date;
	private int userID;
	private Product[] products;
	
	
	public Cart(String  date, int userID, Product[] products) {
		super();
		this.date = date;
		this.userID = userID;
		this.products = products;
	}


	public String getId() {
		return date;
	}


	public void setId(String date) {
		this.date = date;
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
		return "Cart [date=" + date + ", userID=" + userID + ", products=" + Arrays.toString(products) + "]";
	}
	
	
	
}