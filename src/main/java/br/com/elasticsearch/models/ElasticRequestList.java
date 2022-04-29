package br.com.elasticsearch.models;

public class ElasticRequestList {
    private Object hits;

    public Object get_hits() {
        return hits;
    }

    public void set_hits(Object hits) {
        this.hits = hits;
    }
}
