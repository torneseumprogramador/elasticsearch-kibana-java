package br.com.elasticsearch.Services;

import java.io.IOException;
import java.util.TimeZone;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import br.com.elasticsearch.models.Product;

public class RestService {
    public static void post() throws IOException
    {
        Product product = new Product();
        product.setName("Um produto " + TimeZone.getDefault());
        product.setDescription("Uma descricao " + TimeZone.getDefault());

        String jsonFile = new Gson().toJson(product);

        HttpEntity entity = new StringEntity(jsonFile);
        HttpPost post = new HttpPost("http://localhost:9200/products/_doc");
        post.setEntity(entity);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Accept","text/plain");
        var response = client.execute(post);
        System.out.println("Response: " + response);
    }

    public static void count() throws IOException
    {
        HttpGet get = new HttpGet("http://localhost:9200/products/_count");
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        get.addHeader("Content-Type", "application/json");
        get.addHeader("Accept","text/plain");
        var response = client.execute(get);
        String responseAsString = EntityUtils.toString(response.getEntity());
        System.out.println(responseAsString);
    }
}
