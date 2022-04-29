package br.com.elasticsearch;

import java.io.IOException;
import java.util.List;

import br.com.elasticsearch.Services.ElasticService;
import br.com.elasticsearch.Services.RestService;
import br.com.elasticsearch.models.Product;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        // RestService.post();
        // RestService.count();

        // Product productOne = RestService.getOne();
        // System.out.println("Nome: " + productOne.getName());
        // System.out.println("Descrição: " + productOne.getDescription());

        // List<Product> products = RestService.getList();
        // for (Product product : products) {
        //     System.out.println("===============================");
        //     System.out.println("Nome: " + product.getName());
        //     System.out.println("Descrição: " + product.getDescription());
        //     System.out.println("===============================");
        // }

        // listaPaginada(1);

        ElasticService.get();
    }

    private static void listaPaginada(Integer pg) throws IOException {
        List<Product> products = RestService.getPaggedList(pg);
        if(products.size() > 0){
            System.out.println("===============[Pagina: " + pg + "]================");

            for (Product product : products) {
                System.out.println("===============================");
                System.out.println("Nome: " + product.getName());
                System.out.println("Descrição: " + product.getDescription());
                System.out.println("===============================");
            }

            listaPaginada(pg+1);
        }
        
    }
}
