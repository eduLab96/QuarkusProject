package edu;

import edu.api.ApiRequests;
import edu.entities.Product;
import edu.repositories.ProductRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ApiRequestsTest {

    @Inject
    private ApiRequests apiRequests;

    @Inject
    ProductRepository pr;

    @BeforeEach
    public void reset() {
        // Reset table to avoid errors when running all test with .\mvnw quarkus:test
        pr.resetTable();
    }

    @Test
    public void requestProductListTest() {
        assert (!apiRequests.requestProductList().isEmpty());
    }

    @Test
    public void requestPostProductTest() {
        Product p = new Product("RequestApiTestName", "RequestApiTestDesc");
        Assertions.assertTrue(apiRequests.requestPostProduct(p));
    }

    @Test
    public void requestPutProductTest() {
        Product p = new Product(1, "RequestApiTestName", "RequestApiTestDesc");
        Assertions.assertTrue(apiRequests.requestPutProduct(p));

    }

    @Test
    public void requestDeleteProductTest() {
        Product p = new Product(1, "name1", "desc1");
        Assertions.assertTrue(apiRequests.requestDeleteProduct(p));
    }


}
