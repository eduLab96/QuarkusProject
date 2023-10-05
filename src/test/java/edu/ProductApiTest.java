package edu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.entities.Product;
import edu.repositories.ProductRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ProductApiTest {

    @Inject
    ProductRepository pr;

    @BeforeEach
    public void reset() {
        // Reset table to avoid errors when running all test with .\mvnw quarkus:test
        pr.resetTable();
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetEndpoint() {
        given()
                .when().get("/product")
                .then()
                .statusCode(200);
    }

    @Test
    public void testPostEndpoint() throws JsonProcessingException {
        Product p = new Product("postTestName", "postTestDesc");


        String json = objectMapper.writeValueAsString(p);

        given()
                .contentType("application/json")
                .body(json)
                .when().post("/product")
                .then()
                .statusCode(200);
    }

    @Test
    public void testPutEndpoint() throws JsonProcessingException {
        Product p = new Product(1, "putTestName", "putTestDesc");
        String json = objectMapper.writeValueAsString(p);
        given()
                .contentType("application/json")
                .body(json)
                .when().put("/product")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteEndpoint() throws JsonProcessingException {

        Product p = new Product(1, "name1", "desc1");
        String json = objectMapper.writeValueAsString(p);

        given()
                .contentType("application/json")
                .body(json)
                .when().delete("/product")
                .then()
                .statusCode(200);
    }

}
