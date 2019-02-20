

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.methods;
import files.resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class staticJson {
	//Java properties object
	Properties props = new Properties();
		
	@BeforeTest
	public void getData() throws IOException {
		//Load file
		FileInputStream fis = new FileInputStream("/Users/justinasz/eclipse-workspace/RestAssuredTraining/src/files/env.properties");
		props.load(fis);
		
		//Load props
		RestAssured.baseURI = props.getProperty("HOST");
	}
	
	@Test()
	public void addBook() throws IOException {		
		//Create stuff:
		Response resp = given().
			contentType("application/json").
			body(methods.generateStringFromResource("/Users/justinasz/eclipse-workspace/RestAssuredTraining/src/files/addBookDetails.json")).
		when().  
			post(resources.postEndPoint()).
		then().
			assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("Msg", equalTo("successfully added"))
		.extract().response();
		
		JsonPath js = methods.rawToJson(resp);
		String bookId = js.get("ID");
		System.out.println(bookId);
		
		//For cleanup:
		//Does not work with providers, because triggers only after all runs
		//WorkAround:
	}
}