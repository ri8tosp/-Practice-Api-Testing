package practice_api_testing;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginApi;
import pojo.LoginResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class E2E_Ecomm_API {

	public static void main(String[] args) {
		
		
		// LoginTo the app 

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginApi loginRequest = new LoginApi();
		
		loginRequest.setUserEmail("harry@styles.com");
		loginRequest.setUserPassword("ABCD123@abc");
		

		RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);

		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response()
				.as(LoginResponse.class);
		
		
		System.out.println(loginResponse.getToken());
		String token = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId = loginResponse.getUserId();
		
		
		// Creating a product 
		
		RequestSpecification addProductReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		
		RequestSpecification reqAddProduct = given().spec(addProductReq).param("productName", "Bean").param("productAddedBy", userId)
		 .param("productCategory", "fashion").param("productSubCategory", "jacket").param("productPrice", "7373")
		 .param("productDescription", "jacket").param("productFor", "men")
		 .multiPart("productImage", new File(System.getProperty("user.dir") + "/photos/funny baean.png"));
		
		
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
				.then().log().all().extract().asString();
		
		JsonPath js = new JsonPath(addProductResponse);
		
		String productId = js.getString("productId");
		
		

	}

}
