package practice_api_testing;

import io.restassured.path.json.JsonPath;
import payloads.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import static io.restassured.response.ResponseOptions.*;

public class OAuthTestig {

	@Test
	public void getAccessToken() {

		String authTokenString = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").log().all().when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		System.out.println(authTokenString);
		
		JsonPath jp = new JsonPath(authTokenString);
		
		String access_token = jp.getString("access_token");
		
		
		String response = given().log().all().queryParam("access_token", access_token).when()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		
		
		System.out.print(response);
	}

}
