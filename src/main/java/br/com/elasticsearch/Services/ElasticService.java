package br.com.elasticsearch.Services;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import br.com.elasticsearch.models.Product;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

public class ElasticService {
    public static void get() throws ElasticsearchException, IOException{
        RestClient restClient = RestClient.builder(
            new HttpHost("localhost", 9200)).build();

        var restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchTransport transport = restClientTransport;

        ElasticsearchClient client = new ElasticsearchClient(transport);

        // SearchResponse<Product> search = client.search(s -> s
        //     .index("products")
        //     .query(q -> q
        //         .term(t -> t
        //             .field("name")
        //             .value(v -> v.stringValue("banana"))
        //         )),
        //     Product.class);

        SearchResponse<Product> search = client.search(s -> s.index("products"), Product.class);

        for (Hit<Product> hit: search.hits().hits()) {
            processProduct(hit.source());
        }

    }

    private static void processProduct(Product product) {
        System.out.println("-------------------------------");
        System.out.println("Nome: " + product.getName());
        System.out.println("Descricao: " + product.getDescription());
        System.out.println("-------------------------------");
    }


    public static void indexDoc() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

        String jsonObject = "{\"age\":10,\"dateOfBirth\":1471466076564,\"fullName\":\"John Doe\"}";
        IndexRequest request = new IndexRequest("people");
        request.source(jsonObject, XContentType.JSON);
        
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        String index = response.getIndex();
        long version = response.getVersion();
            
        if(Result.CREATED == response.getResult()){
            System
        }
    }    
}
