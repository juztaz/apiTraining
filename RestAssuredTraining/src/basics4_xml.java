import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.methods;
import files.resources;

import static io.restassured.RestAssured.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class basics4_xml {
	
	//Java properties object
		Properties props = new Properties();
		
		@BeforeTest
		public void getData() throws IOException {
			//Enable logging if failed:
			RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
			
			//Load file
			FileInputStream fis = new FileInputStream("/Users/justinasz/eclipse-workspace/RestAssuredTraining/src/files/env.properties");
			props.load(fis);
			
			//Get base URI
			RestAssured.baseURI = props.getProperty("HOST");
		}
	
	@Test
	public void testPost() throws Exception {
		String postData = methods.generateStringFromResource("/Users/justinasz/eclipse-workspace/RestAssuredTraining/src/files/postData.xml");
		
		//Create stuff:
		Response res = 
		given().
			contentType("application/xml").
			queryParam("key", props.getProperty("QA_KEY")).
			body(postData).log().all().
		when().
			post(resources.xmlAddEndPoint()).
		then().
			assertThat().statusCode(200).and().contentType(ContentType.XML).
		extract().response();
		
		//XML parser
		XmlPath xml = methods.rawToXml(res);
		System.out.println(xml.get("response.status").toString());
	}
}
