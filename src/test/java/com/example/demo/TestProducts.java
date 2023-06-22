package com.example.demo;

import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;


@SpringBootTest(classes =TestProducts.class)
class TestProducts {

	String apiAdress = "http://localhost:8080/products";
	String searchNameEndpoint = "/search-name";
	String limitByPriceEndpoint = "/price-limit";
	String newProductAPI = "/add-product";


	@Test
	@DisplayName("When asking for all clients, we should see all of them")
	public void getAllProducts() {
		given().
			contentType(ContentType.JSON)
		.when()
			.get(apiAdress)
		.then()
				.statusCode(200)
				.assertThat().body(containsString("Nachos"));
	}

	@Test
	@DisplayName("When asking for a specif items, we should see it")
	public void getMeTacos() {
		given().
				contentType(ContentType.JSON)
				.when()
				.get(apiAdress+searchNameEndpoint)
				.then()
				.statusCode(200)
				.assertThat().body(containsString("Tacos"));
	}

	@Test
	@DisplayName("When asking for items, we should see them")
	public void belowThirtyBucks() {
		given().
				contentType(ContentType.JSON)
				.when()
				.get(apiAdress+limitByPriceEndpoint)
				.then()
				.statusCode(200)
				.assertThat().body(containsString("Tacos"))
				.assertThat().body(containsString("Nachos"));
	}

	@Test
	@DisplayName("When creating an user then it should be available")
	public void createUser(){

		String createProduct = "{\n" +
				"  \"name\": \"Quesadilla\",\n" +
				"  \"price\": 20.90,\n" +
				"  \"description\": \"Tortilla com queijo\"\n" +
				"}";

		String expectedAnswer = "\"name\":\"Quesadilla\"";

		given()
				.contentType(ContentType.JSON)
				.body(createProduct)
				.when()
				.post(apiAdress+newProductAPI)
				.then()
				.statusCode(201)
				.assertThat().body(containsString(expectedAnswer));

	}

	@Test
	@DisplayName("When updating a product, the data should change")
	public void updateProduct(){

		String updateProduct = "{\n" +
				"  \"name\": \"Quesadilla\",\n" +
				"  \"price\": 21.90,\n" +
				"  \"description\": \"Tortilla com queijo\"\n" +
				"}";

		String expectedAnswer = "\"price\": \"21.90\"";

		given().contentType(ContentType.JSON).body(updateProduct)
				.when().put(apiAdress + "/4")
				.then().statusCode(200).assertThat().body(containsString(expectedAnswer));

	}
	@Test
	@DisplayName("When updating a product, the data should change")
	public void deleteProduct(){

		String expectedAnswer = "";

		given().contentType(ContentType.JSON)
				.when().delete(apiAdress+"/2")
				.then().statusCode(204)
				.assertThat().body(new IsEqual<>(expectedAnswer));
	}

}
