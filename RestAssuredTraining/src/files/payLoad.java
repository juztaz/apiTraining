package files;

import java.util.Random;

public class payLoad {

	public static String postData() {
		Random rand = new Random();
		int n0 = rand.nextInt(100000);
		int n1 = rand.nextInt(100000);
		String postData = "{\n" + 
				"\"name\":\"Justinas-InsertedFromTestNG\",\n" + 
				"\"isbn\":\"" + n0 + "\",\n" + 
				"\"aisle\":\"" + n1 + "\",\n" + 
				"\"author\":\"Robot\"\n" + 
				"}\n" + 
				" \n" + 
				"";
		return postData;
	}
	
	public static String deleteData(String bookId) {
		String deleteData = "{\n" + 
				"\"ID\" : \"" + bookId + "\"\n" + 
				"}";
		
		return deleteData;
	}
	
	public static String postDataWithVars(String isbn, String aisle) {
		String postData = "{\n" + 
				"\"name\":\"Justinas-InsertedFromTestNG\",\n" + 
				"\"isbn\":\"" + isbn + "\",\n" + 
				"\"aisle\":\"" + aisle + "\",\n" + 
				"\"author\":\"Robot\"\n" + 
				"}\n" + 
				" \n" + 
				"";
		return postData;
	}
	
	public static String newBug(String projectKey, String summary, String description) {

		String postData = "{\n" + 
				"	\"fields\": {\n" + 
				"		\"project\": {\n" + 
				"			\"key\": \"" + projectKey + "\"\n" + 
				"		},\n" + 
				"	\"summary\": \"" + summary + "\",\n" + 
				"	\"description\": \"" + description + "\",\n" + 
				"	\"issuetype\": {\n" + 
				"		\"name\": \"Bug\"\n" + 
				"		}\n" + 
				"	}\n" + 
				"}";
		
		return postData;
	}
	
	public static String newComment(String comment) {
		String postData = "{\n" + 
				"  \"body\": \"" + comment + "\",\n" + 
				"  \"visibility\": {\n" + 
				"    \"type\": \"role\",\n" + 
				"    \"value\": \"Administrators\"\n" + 
				"  }\n" + 
				"}";
		return postData;
	}
}
