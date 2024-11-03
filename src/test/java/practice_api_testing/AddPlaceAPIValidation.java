package practice_api_testing;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.response.ResponseOptions.*;

public class AddPlaceAPIValidation {

	public static void main(String[] args) throws IOException {

		// Validationg that you are able to add a place successfully using an external
		// Json Data file
		// approach used is Use File.readAllByets() to read json data - > to bytes - > then convert them into string

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String responseOutput = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body( new String (Files.readAllBytes(
						Paths.get(System.getProperty("user.dir") + "/src/test/java/payloads/payloadData.json"))))
				.when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		JsonPath js = new JsonPath(responseOutput); // for parsing the string & converting into JSON & extracting data
													// out it
		String place_id = js.getString("place_id"); 

		System.out.println("The place id is : " + place_id);

	}

}
