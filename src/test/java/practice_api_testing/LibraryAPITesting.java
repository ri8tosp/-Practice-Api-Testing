package practice_api_testing;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payloads.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.response.ResponseOptions.*;

public class LibraryAPITesting {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().header("Content-Type", "application/json").body(Payload.addBookPayload(isbn, aisle)).when()
				.post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		JsonPath js = new JsonPath(response);
		
		String id = js.get("ID");
	}
	
	
	@DataProvider(name = "BooksData")
	public Object[][] passBookData()
	{
		return new Object [][] {{"abcde","6483"}, {"idd","737"}, {"yddu","7839"}, {"dhd","755"}};
	}

}
