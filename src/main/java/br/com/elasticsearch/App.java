package br.com.elasticsearch;

import java.io.IOException;

import br.com.elasticsearch.Services.ElasticService;
import br.com.elasticsearch.Services.RestService;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        RestService.postGet();
        // ElasticService.get();
    }
}
