import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import files.resources;
import files.methods;
import files.payLoad;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basics3 {
	
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
	
	
	@Test
	public void addAndDeletePlace() {
		
		//Create stuff and take whole response
		Response res =
		given().
			body(payLoad.postData()).
		when().
			post(resources.postEndPoint()).
		then().
			assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("Msg", equalTo("successfully added")).
		extract().
			response();
		
		//Take the ID
		JsonPath js = methods.rawToJson(res);
		String bookId = js.get("ID");
		
		//Book ID in Delete request
		given().
			body(payLoad.deleteData(bookId)).
		when().
			post(resources.deleteEndPoint()).
		then().
			assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("msg", equalTo("book is successfully deleted"));
	}

}
