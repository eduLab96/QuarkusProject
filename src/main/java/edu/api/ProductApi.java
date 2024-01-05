package edu.api;

import java.util.List;
import edu.entities.Product;
import edu.repositories.ProductRepository;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductApi {

    ProductRepository pr;

    @Inject
    public ProductApi(ProductRepository pr) {
        this.pr = pr;
    }

    @GET
    public List<Product> list_old() {
        return pr.listProducts();
    }
  
    @GET
    @Path("/query")
    public List<Product> list(@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
        return pr.listProductsGrid(offset, limit);
    }
    
    

    @POST
    public Response add(Product p){
        try {
            pr.createProduct(p);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @DELETE
    public Response delete(Product p ) {
        pr.deleteProduct(p);
        return Response.ok().build();
    }

    @PUT
    public Response update(Product p) {

        try {
            pr.updateProduct(p);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.notModified().build();
        }
        return Response.ok().build();
    }


}

