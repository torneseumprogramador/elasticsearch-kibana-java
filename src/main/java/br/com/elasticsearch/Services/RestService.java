package br.com.elasticsearch.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import br.com.elasticsearch.models.ElasticRequest;
import br.com.elasticsearch.models.ElasticRequestList;
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

    public static Product getOne() throws IOException
    {
        HttpGet get = new HttpGet("http://localhost:9200/products/_doc/1");
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        get.addHeader("Content-Type", "application/json");
        get.addHeader("Accept","text/plain");
        var response = client.execute(get);
        
        String responseAsString = EntityUtils.toString(response.getEntity());

        var elasticRequest = new Gson().fromJson(responseAsString, ElasticRequest.class);
        
        var product = new Gson().fromJson(new Gson().toJson(elasticRequest.get_source()), Product.class);

        return product;
    }

    public static List<Product> getList() throws IOException
    {
        HttpGet get = new HttpGet("http://localhost:9200/products/_search");
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        get.addHeader("Content-Type", "application/json");
        get.addHeader("Accept","text/plain");
        var response = client.execute(get);
        
        String responseAsString = EntityUtils.toString(response.getEntity());

        var elasticRequestList = new Gson().fromJson(responseAsString, ElasticRequestList.class);
        
        List<Product> products = new ArrayList<>();

        var hits = (List<LinkedTreeMap>)((LinkedTreeMap)elasticRequestList.get_hits()).get("hits");
        
        for (var productUnmapped : hits) {
            var product_json = new Gson().toJson(productUnmapped.get("_source"));
            var product = new Gson().fromJson(product_json, Product.class);
            products.add(product);
        }

        return products;
    }

    public static List<Product> getPaggedList(Integer pg) throws IOException
    {
        HttpGet get = new HttpGet("http://localhost:9200/products/_search?size=10&from=" + (pg-1));
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        get.addHeader("Content-Type", "application/json");
        get.addHeader("Accept","text/plain");
        var response = client.execute(get);
        
        String responseAsString = EntityUtils.toString(response.getEntity());

        var elasticRequestList = new Gson().fromJson(responseAsString, ElasticRequestList.class);
        
        List<Product> products = new ArrayList<>();

        var hits = (List<LinkedTreeMap>)((LinkedTreeMap)elasticRequestList.get_hits()).get("hits");

        if(hits.size() < 1) return products;
        
        for (var productUnmapped : hits) {
            var product_json = new Gson().toJson(productUnmapped.get("_source"));
            var product = new Gson().fromJson(product_json, Product.class);
            products.add(product);
        }

        return products;
    }
}