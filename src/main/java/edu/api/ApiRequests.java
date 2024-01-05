package edu.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import edu.entities.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@RequestScoped
public class ApiRequests {

    /*
        This class manages all the requests sent to the API from the MainView
     */

    ObjectMapper objectMapper;

    @Inject
    public ApiRequests(ObjectMapper om) {
        this.objectMapper = om;
    }

    
    public List<Product> requestProductListQuery(int offset, int limit)  {
        HttpRequest getRequest;
        try {
            getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/product/query?offset=" + offset + "&limit=" + limit)) //get?param1=value1&param2=value2"
                    .GET().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse;
        try {
            getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return jsonProductsToList(getResponse.body());
    }


    public boolean requestPostProduct(Product p)  {
        String json = productToJson(p);
        HttpRequest postRequest;
        try {
            postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/product"))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse;
        try {
            postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            if (postResponse.statusCode() == 200) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean requestPutProduct(Product p)  {
        String json = productToJson(p);
        HttpRequest postRequest;
        try {
            postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/product"))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse;
        try {
            postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            if (postResponse.statusCode() == 200) {
                 return true;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }

        return false;
    }

    public boolean requestDeleteProduct(Product p)  {
        String json = productToJson(p);
        HttpRequest postRequest;
        try {
            postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/product"))
                    .method("DELETE", HttpRequest.BodyPublishers.ofString(json))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse;
        try {
            postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            if (postResponse.statusCode() == 200) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;

        }
        return false;
    }

    public List<Product> jsonProductsToList(String json) {
        List<Product> productList;
        try {
            productList = objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public String productToJson(Product p) {

        try {
            return objectMapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
