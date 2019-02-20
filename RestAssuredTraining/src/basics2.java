import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.methods;
import files.payLoad;
import files.resources;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basics2 {
	
	//Java properties object
	Properties props = new Properties();
	
	//Pre-define bookId variable
	String bookToDelete = "empty";
	ArrayList<String> listToDelete = new ArrayList<String>();
		
	@BeforeTest
	public void getData() throws IOException {
		//Enable logging if failed:
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		//Load file
		FileInputStream fis = new FileInputStream("/Users/justinasz/eclipse-workspace/RestAssuredTraining/src/files/env.properties");
		props.load(fis);
		
		//Load props
		RestAssured.baseURI = props.getProperty("HOST");
	}
	
	@AfterTest
	public void cleanUpData() {
		if(!listToDelete.isEmpty()) {
			listToDelete.forEach((n) -> deleteBook(n));
		}
	}
	
	@DataProvider(name = "booksData")
	public Object[][] getAllData() {
		//Multi-dimensional array for data, each array = 1 run
		return new Object[][] {{"is1001", "1001"}, {"is2002", "2002"}, {"is3003", "3003"}};
	}
	
	@Test(dataProvider = "booksData")
	public void addBook(String isbn, String aisle) {		
		//Create stuff:
		Response resp = given().
			contentType("application/json").
			body(payLoad.postDataWithVars(isbn, aisle)).
		when().  
			post(resources.postEndPoint()).
		then().
			assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
			body("Msg", equalTo("successfully added"))
		.extract().response();
		
		JsonPath js = methods.rawToJson(resp);
		String bookId = js.get("ID");
		
		//For cleanup:
		//Does not work with providers, because triggers only after all runs
		//WorkAround:
		listToDelete.add(bookId);
	}
	
	private void deleteBook(String bookId) {
		given().
			body(payLoad.deleteData(bookId)).
		when().
   			post(resources.deleteEndPoint()).
   		then().
   			assertThat().statusCode(200).and().
   			body("msg", equalTo("book is successfully deleted"));

		System.out.println("Deleted: " + bookId);
	}

}
