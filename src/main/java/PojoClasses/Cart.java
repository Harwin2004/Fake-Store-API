package PojoClasses;


public class Cart {

    private int userId;
    private String date;
    private Prod[] products;

    // Default constructor
    public Cart() {}

    public Cart(int userId, String date, Prod[] products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Prod[] getProducts() {
        return products;
    }

    public void setProducts(Prod[] products) {
        this.products = products;
    }
}
