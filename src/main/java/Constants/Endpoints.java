package Constants;

public interface Endpoints {
	
	//Product module endpoints
	
	String PRODUCT_POST = "/products";
	String PRODUCT_GET_ALL_PRODUCT = "/products";
	String PRODUCT_GET_SINGLE_PRODUCT = "/products/{id}";
	String PRODUCT_UPDATE = "/products/{id}";
	String PRODUCT_DELETE = "/products/{id}";
	
	//Carts module endpoints
	
	String CARTS_POST = "/carts";
	String CARTS_GET_ALL_PRODUCT = "/carts";
	String CARTS_GET_SINGLE_PRODUCT = "/carts/{id}";
	String CARTS_UPDATE = "/carts/{id}";
	String CARTS_DELETE = "/carts/{id}";
	
	//User module endpoints
	
	String USERS_POST = "/users";
	String USERS_GET_ALL_PRODUCT = "/users";
	String USERS_GET_SINGLE_PRODUCT = "/users/{id}";
	String USERS_UPDATE = "/users/{id}";
	String USERS_DELETE = "/users/{id}";
	
	//Auth module endpoints
	
	String AUTH_POST = "/auth/login";
}
