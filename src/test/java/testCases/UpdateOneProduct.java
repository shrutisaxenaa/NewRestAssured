package testCases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateOneProduct {
	
	SoftAssert softAssert= new SoftAssert();
	String baseUri="https://techfios.com/api-prod/api/product";
	String createProductRequestHeader="application/json; charset=UTF-8";
	String createProductResponseHeader;
	String updateProductRequestHeader="application/json; charset=UTF-8";
	String bearerToken="Bearer gbfbvvxcxcxzcxzcxz";
	String createProductActualMessage;
	String createProductExpectedMessage="Product was created.";
	String firstProductID;
	HashMap<String,String> createPayloadMap;
	HashMap<String,String> upatePayloadMap;
	String updateProductResponseHeader;
	String updateProductExpectedMessage="Product was updated.";
	String updateProductActualMessage;
	
	
	public Map<String,String> getCreatePayloadMap(){
	
		createPayloadMap= new HashMap<String,String>();
		
		createPayloadMap.put("name", "Amazing jewel pieces By SS 2.0");
		createPayloadMap.put("description", "The best Diamond for everyone.");
		createPayloadMap.put("price", "1000");
		createPayloadMap.put("category_id", "1");
		createPayloadMap.put("category_name", "Fashion");
		
		return createPayloadMap;
	}
	
	public Map<String,String> getUpdatePayloadMap(){
		
		upatePayloadMap= new HashMap<String,String>();
		
		upatePayloadMap.put("id", firstProductID);
		upatePayloadMap.put("name", "Amazing jewel pieces By SNS 5.0");
		upatePayloadMap.put("description", "The best Diamond for everyone.");
		upatePayloadMap.put("price", "1000");
		upatePayloadMap.put("category_id", "1");
		upatePayloadMap.put("category_name", "Fashion");
		
		return upatePayloadMap;
	}
	
	@Test(priority=1)
public void createOneProduct() {
				
		Response response =
				
				given()
					.baseUri(baseUri)
					.header("Content-Type","application/json; charset=UTF-8")
					.header("Authorization",bearerToken)
//					.body(new File("Data\\createPayload.json")).
					.body(getCreatePayloadMap()).
		
    when()
         
         .post("/create.php").
         
    then()
    
          .extract().response();
		
		int statusCode=	response.getStatusCode();
		System.out.println("Status Code :"+statusCode);
		softAssert.assertEquals(statusCode, 201);
		
		
			
		 createProductResponseHeader=response.getHeader("Content-Type");
		
		System.out.println("Response Header is:"+createProductResponseHeader);
		softAssert.assertEquals(createProductResponseHeader, createProductRequestHeader);
		
		String responseBody=response.getBody().asString();
		
		JsonPath jsonPath= new JsonPath(responseBody);
		
		createProductActualMessage=jsonPath.getString("message");
		System.out.println("Product Message: "+createProductActualMessage);
		softAssert.assertEquals(createProductActualMessage, createProductExpectedMessage);
	
		
		softAssert.assertAll();
}
	
	public class ReadAllProducts {
		
		@Test(priority=2)
		public void readAllProducts() {
			
		Response response =
			
			given()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.auth().preemptive().basic("demo@techfios.com", "abc123").
			when()
			        .get("/read.php").
			        					        	    
			then()
			       .extract().response();
			
			String responseHeader=response.getHeader("Content-Type");
			
			System.out.println("Response Header is:"+responseHeader);
			Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");	
			
			String responseBody=response.getBody().asString();
			
			JsonPath jsonPath= new JsonPath(responseBody);
			
			 firstProductID=jsonPath.get("records[0].id");
			System.out.println("First Product ID :"+firstProductID);
		
			
		}	
							
							
		}

		@Test(priority=4)
		public void readOneProduct() {
			
			
			Response response =
			
		given()
			.baseUri(baseUri)
			.header("Content-Type","application/json")
			.header("Authorization","Bearer gbfbvvxcxcxzcxzcxz")
			.queryParam("id", firstProductID).
						
	    when()
	         
	         .get("/read_one.php").
	         
	    then()
	    
	          .extract().response();
			
		String responseBody=response.getBody().asString();
						
		JsonPath jsonPath= new JsonPath(responseBody);
		
		String actualProductNameFromResponse=jsonPath.getString("name");
		System.out.println("Product name: "+actualProductNameFromResponse);
		softAssert.assertEquals(actualProductNameFromResponse, getUpdatePayloadMap().get("name"));
		
		String productDescription= jsonPath.getString("description"); 
		System.out.println("Product Descrition: "+productDescription);
		softAssert.assertEquals(productDescription, getUpdatePayloadMap().get("description"));
		
		String productPrice=jsonPath.getString("price");
		System.out.println("product price :"+productPrice);
		softAssert.assertEquals(productPrice,getUpdatePayloadMap().get("price"));
		
		String productCategoryId=jsonPath.getString("category_id");
		softAssert.assertEquals(productCategoryId, getUpdatePayloadMap().get("category_id"));
		
		softAssert.assertAll();
		}
		
		
		@Test(priority=3)
		public void upateOneProduct() {
			
			Response response =
					
					given()
						.baseUri(baseUri)
						.header("Content-Type","application/json; charset=UTF-8")
						.header("Authorization",bearerToken)
//						.body(new File("Data\\createPayload.json")).
						.body(getUpdatePayloadMap()).
			
	    when()
	         
	         .put("/update.php").
	         
	    then()
	    
	          .extract().response();
			
			int statusCode=	response.getStatusCode();
			System.out.println("Status Code :"+statusCode);
			softAssert.assertEquals(statusCode, 200);
			
			
				
			 updateProductResponseHeader=response.getHeader("Content-Type");
			
			System.out.println("Response Header is:"+updateProductResponseHeader);
			softAssert.assertEquals(updateProductResponseHeader, updateProductRequestHeader);
			
			String responseBody=response.getBody().asString();
			
			JsonPath jsonPath= new JsonPath(responseBody);
			
			updateProductActualMessage=jsonPath.getString("message");
			System.out.println("Product Message: "+updateProductActualMessage);
			softAssert.assertEquals(updateProductActualMessage, updateProductExpectedMessage);
		
			
			softAssert.assertAll();
	}
		

	}
	
