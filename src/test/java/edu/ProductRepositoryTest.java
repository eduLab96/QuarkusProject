package edu;

import edu.entities.Product;
import edu.repositories.ProductRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest

public class ProductRepositoryTest {
/*
This test class could be annotated with @TestTransaction to avoid having to reset the table
But then it fails when all tests are run at the same time
Since the Api test do persist the changes
 */
    @Inject
    ProductRepository pr;

    @BeforeEach
    public  void reset() {
        // Reset table to avoid errors when running all test with .\mvnw quarkus:test
        pr.resetTable();
    }

    @Test
    public void testGetProductById() {
        Product p = pr.getProductById(1);
        Assertions.assertNotNull(p);
        Assertions.assertEquals("name1", p.getName());

    }

    @Test
    public void testDeleteProduct() {
        Product p = new Product(1, "name1", "desc1");
        pr.deleteProduct(p);
        Assertions.assertNull(pr.getProductById(1));
    }

    @Test
    public void testCreateProduct() {
        Product p = new Product("testName", "testDesc");
        pr.createProduct(p);
        Assertions.assertNotNull(pr.getProductById(2)); //this product will have id 2 since the table is reset with 1 record
        Assertions.assertEquals("testName", p.getName());
    }

    @Test
    public void testListProducts() {
        Assertions.assertFalse(pr.listProducts().isEmpty());
    }

    @Test
    public void testUpdateProduct() {
        Product p = new Product(1, "testName", "testDesc");
        pr.updateProduct(p);
        Assertions.assertEquals("testName", pr.getProductById(1).getName());
    }

}
