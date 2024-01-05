package edu.repositories;

import java.util.List;
import edu.entities.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductRepository {

    EntityManager em;
    @Inject
    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void createProduct(Product p) {
        em.persist(p);
    }

    @Transactional
    public void deleteProduct(Product p) {

        em.remove(em.find(Product.class, p.getId()));
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Product> listProducts() {
        String hql = "SELECT p FROM Product p";
        return em.createQuery(hql).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Product> listProductsGrid(int offset, int limit){
        String hql = "SELECT p FROM Product p";
        return em.createQuery(hql)
        		.setFirstResult(offset)
        		.setMaxResults(limit)
        		.getResultList();
    }

    @Transactional
    public Product getProductById(int id) {
        return em.find(Product.class, id);
    }

    @Transactional
    public void updateProduct(Product p) {
        em.merge(p);
    }

    @Transactional
    public void resetTable(){
        /*
             This function is only meant for testing
             1. It deletes everything from the table
             2. It drops the id column and then adds it again to restart the automatic id
             3. Inserts the same product as in import.sql
         */

        String hql = "Delete FROM Product";
        Query query = em.createQuery(hql);
        query.executeUpdate();

        hql = "ALTER TABLE Product DROP COLUMN id";
        query = em.createNativeQuery(hql);
        query.executeUpdate();

        hql = "ALTER TABLE Product Add COLUMN id INT AUTO_INCREMENT";
        query = em.createNativeQuery(hql);
        query.executeUpdate();


        Product p = new Product("name1", "desc1");
        em.persist(p);
    }

}
