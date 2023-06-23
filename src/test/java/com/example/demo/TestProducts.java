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
	String searchNameEndpoint = "/search?name=Tacos";
	String limitByPriceEndpoint = "/price-limit/30";
	String newProductEndpoint = "/add-product";
	String updateProductEndpoint = "/update-product/1";


	@Test
	@DisplayName("Quando o cliente procurar por todos os produtos cadastrados " +
			"no import.sql , os três são exibidos")
	public void getStarterCombo() {
		given().
			contentType(ContentType.JSON)
		.when()
			.get(apiAdress)
		.then()
				.statusCode(200)
				.assertThat().body(containsString("nachos"))
				.assertThat().body(containsString("tacos"))
				.assertThat().body(containsString("burrito"));
	}

	@Test
	@DisplayName("Quando o cliente procurar pelo nome de um produto, " +
			"no caso, Tacos, ele deverá ser exibido")
	public void getMeTacos() {
		given().
				contentType(ContentType.JSON)
				.when()
				.get(apiAdress+searchNameEndpoint)
				.then()
				.statusCode(302)
				.assertThat().body(containsString("tacos"));
	}

	@Test
	@DisplayName("Quando o cliente procurar por produtos com um valor menor que " +
			"30 reais, eles deverão ser exibidos")
	public void imBroke() {
		given().
				contentType(ContentType.JSON)
				.when()
				.get(apiAdress+limitByPriceEndpoint)
				.then()
				.statusCode(200)
				.assertThat().body(containsString("tacos"))
				.assertThat().body(containsString("nachos"));
	}

	@Test
	@DisplayName("Quando um produto for cadastrado, este deverá ser adicionado ao banco de dados")
	public void createProduct(){

		String createProduct = "{\n" +
				"  \"name\": \"quesadilla\",\n" +
				"  \"price\": 20.90,\n" +
				"  \"description\": \"Quesadilla com queijo\"\n" +
				"}";

		String expectedAnswer = "\"name\":\"quesadilla\"";

		given()
				.contentType(ContentType.JSON)
				.body(createProduct)
				.when()
				.post(apiAdress+newProductEndpoint)
				.then()
				.statusCode(201)
				.assertThat().body(containsString(expectedAnswer));

	}

	@Test
	@DisplayName("Quando o valor de um produto JÁ CADASTRADO (ex: tacos) precisar ser alterado, " +
			"este campo deverá ser atualizado no banco")
	public void updateProduct(){

		String updateProduct = "{\n" +
				"  \"name\": \"tacos\",\n" +
				"  \"price\": 28.90,\n" +
				"  \"description\": \"Tacos de milho com recheio de frango\"\n" +
				"}";

		String expectedAnswer = "\"price\":28.9";

		given().contentType(ContentType.JSON)
				.body(updateProduct)
				.when()
				.put(apiAdress +updateProductEndpoint)
				.then().statusCode(200)
				.assertThat().body(containsString(expectedAnswer));

	}
	@Test
	@DisplayName("Quando o burrito for excluído ele deve estar ausente do banco de dados")
	public void deleteBurrito(){

		String expectedAnswer = "";

		given().contentType(ContentType.JSON)
				.when().delete(apiAdress+"/2")
				.then().statusCode(204)
				.assertThat().body(new IsEqual<>(expectedAnswer));
	}

}
