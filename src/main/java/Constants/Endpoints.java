package Constants;

public interface Endpoints {
	
	//Product module endpoints
	
	String PRODUCT_POST = "/products";
	String PRODUCT_GET_ALL_PRODUCT = "/products";
	String PRODUCT_GET_SINGLE_PRODUCT = "/products";
	String PRODUCT_UPDATE = "/product";
	String PRODUCT_DELETE = "/product";
	
	//Carts module endpoints
	
	String CARTS_POST = "/carts";
	String CARTS_GET_ALL_PRODUCT = "/carts";
	String CARTS_GET_SINGLE_PRODUCT = "/carts";
	String CARTS_UPDATE = "/carts";
	String CARTS_DELETE = "/carts";
	
	//User module endpoints
	
	String USERS_POST = "/users";
	String USERS_GET_ALL_PRODUCT = "/users";
	String USERS_GET_SINGLE_PRODUCT = "/users";
	String USERS_UPDATE = "/users";
	String USERS_DELETE = "/users";
	
	//Auth module endpoints
	
	String AUTH_POST = "/auth/login";
}
