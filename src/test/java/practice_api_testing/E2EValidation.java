package practice_api_testing;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.Payload;

public class E2EValidation {

	public static void main(String[] args) {

		// Validate that u are able to add a place - > update that place's address & ->
		// get that place using place_id

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Addding a place first

		String responseOutput = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(Payload.addPlace()).when()
				.post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		JsonPath js = new JsonPath(responseOutput); // for parsing the string & converting into JSON & extracting data
													// out it
		String place_id = js.getString("place_id");

		// Updating the address of that place

		String updatedAddress = "Google";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\n" + "\"place_id\":\"" + place_id + "\",\n" + "\"address\":\"" + updatedAddress + "\",\n"
						+ "\"key\":\"qaclick123\"\n" + "}")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		
		
		

		// using get call to verify that the response has the latest Address

		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();
		
		

		JsonPath jsp = new JsonPath(getPlaceResponse);
		
		String actualResonseAddress = jsp.getString("address");
		
		Assert.assertEquals(actualResonseAddress, updatedAddress);

	}

}
