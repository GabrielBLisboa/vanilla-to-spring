package com.example.demo;

import io.restassured.http.ContentType;
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

}
