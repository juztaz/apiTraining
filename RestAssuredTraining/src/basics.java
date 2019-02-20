import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import files.resources;

public class basics {

	 
	 
	@Test
	public void Test1() {
		//BaseUrl
		RestAssured.baseURI = "http://216.10.245.166";
		
		//1st - given(), 2nd - when(), 3rd - then(), 4th - extract()
		/*given():
		headers
		parameters
		cookies
		
		when():
		get(resource)
		post(resource)
		etc(resource) 
		
		then():
		assertions
		 
		extract():
		save some stuff for future
		*/
		given().
				param("ID", "bczd0022701").log().all().
		when().
				get(resources.getEndPoint("bczd0022701")).
		then().
				assertThat().statusCode(200).and().contentType(ContentType.JSON).
				and().body("isbn", contains("bczd00")).
				and().body("aisle", contains("22701")).
				and().header("Server", "Apache");

	}

}
