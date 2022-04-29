# Elastic Stack
- https://www.elastic.co/pt/

### ElasticSearch 
- O Elasticsearch é um mecanismo de busca e análise de dados distribuído, gratuito e aberto para todos os tipos de dados, incluindo textuais, numéricos, geoespaciais, estruturados e não estruturados. O Elasticsearch é desenvolvido sobre o Apache Lucene e foi lançado pela primeira vez em 2010 pela Elasticsearch N.V. (agora conhecida como Elastic). Conhecido por suas REST APIs simples, natureza distribuída, velocidade e escalabilidade, o Elasticsearch é o componente central do Elastic Stack, um conjunto de ferramentas gratuitas e abertas para ingestão, enriquecimento, armazenamento, análise e visualização de dados. Comumente chamado de ELK Stack (pelas iniciais de Elasticsearch, Logstash e Kibana), o Elastic Stack agora inclui uma rica coleção de agentes lightweight conhecidos como Beats para enviar dados ao Elasticsearch.
- https://www.elastic.co/pt/what-is/elasticsearch
### Kibana
- O Kibana é uma aplicação gratuita e aberta de front-end que trabalha com o Elastic Stack, fornecendo recursos de busca e visualização de dados indexados no Elasticsearch. Comumente conhecido como a ferramenta de gráficos para o Elastic Stack (que anteriormente chamava-se ELK Stack após o Elasticsearch, o Logstash e o Kibana), o Kibana também atua como interface do usuário para monitorar, gerenciar e proteger um cluster do Elastic Stack, além de ser o hub centralizado para soluções integradas desenvolvidas no Elastic Stack. Desenvolvido em 2013 a partir da comunidade do Elasticsearch, o Kibana cresceu e se tornou a janela de acesso ao próprio Elastic Stack, oferecendo um portal para usuários e empresas. 
- https://www.elastic.co/pt/what-is/kibana
### Logstash 
- Sistema de log que trabalha integrado com ElasticSearch com ele é possível enviar informações de log do ElasticSearch deixando o histórico de informações gravadas
- O Logstash é um pipeline gratuito e aberto de processamento de dados do lado do servidor que faz a ingestão de dados de inúmeras fontes, transforma-os e envia-os para o seu "esconderijo" favorito.
- https://www.elastic.co/pt/logstash/
### Beats
- Uma espécie de sender que envia dados para o ElasticSearch e Logstash (agente coletor de dados) tipo spark
- O Beats é uma plataforma gratuita e aberta para agentes de dados de finalidade única. Eles enviam dados de centenas ou milhares de computadores e sistemas para o Logstash ou o Elasticsearch.
- https://www.elastic.co/pt/beats/
<hr>

# Install elasticsearch
- https://www.elastic.co/guide/en/elasticsearch/reference/current/install-elasticsearch.html
```shell
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.1.3-darwin-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.1.3-darwin-x86_64.tar.gz.sha512
shasum -a 512 -c elasticsearch-8.1.3-darwin-x86_64.tar.gz.sha512 
tar -xzf elasticsearch-8.1.3-darwin-x86_64.tar.gz
cd elasticsearch-8.1.3/
./bin/elasticsearch
```

# configurar segurança do elasticsearch
```shell
vim config/elasticsearch.yml
```
```yml
#discovery.seed_hosts: ["host1", "host2"]
network.host: 0.0.0.0 # habilita para todos os hosts
http.port: 9200  # seleciona porta
discovery.seed_hosts: [0.0.0.0]  # habilita para todos os hosts
#
# Bootstrap the cluster using an initial set of master-eligible nodes:
#
#cluster.initial_master_nodes: ["node-1", "node-2"]
#
# For more information, consult the discovery and cluster formation module documentation.
#
# ---------------------------------- Various -----------------------------------
#
# Allow wildcard deletion of indices:
#
#action.destructive_requires_name: false
 
#----------------------- BEGIN SECURITY AUTO CONFIGURATION -----------------------
#
# The following settings, TLS certificates, and keys have been automatically     
# generated to configure Elasticsearch security features on 28-04-2022 14:28:52
#
# --------------------------------------------------------------------------------
 
# Enable security features
xpack.security.enabled: false # desabilita segurança para conseguirmos acessar via rest
xpack.security.enrollment.enabled: false # desabilita segurança para conseguirmos acessar via rest
```


# install Kibana
- gerenciador/client de dados do elasticsearch de forma visual
- https://www.elastic.co/guide/en/kibana/current/install.html
```shell
curl -O https://artifacts.elastic.co/downloads/kibana/kibana-8.1.3-darwin-x86_64.tar.gz
curl https://artifacts.elastic.co/downloads/kibana/kibana-8.1.3-darwin-x86_64.tar.gz.sha512 | shasum -a 512 -c - 
tar -xzf kibana-8.1.3-darwin-x86_64.tar.gz
cd kibana-8.1.3/
./bin/kibana
```

- configurar o seu .bash_profile ou .bashrc
```shell
code ~/.bash_profile

alias "elasticsearch_start= ~/elasticsearch-8.1.3/bin/elasticsearch"
alias "kibana_start= ~/kibana-8.1.3/bin/kibana"

source ~/.bash_profile
```

# Acessos
- http://localhost:9200/ # elasticsearch
- http://localhost:5601 # kibana


# utilizando Kibaba
```javascript
# http://localhost:9200/_search
GET _search
{
  "query":{
    "match_all" : {}
  }
}

# http://localhost:9200/[INDICE]/_doc
POST products/_doc
{
  "name": "Limão",
  "description": "Limão caetano"
}

# http://localhost:9200/[INDICE]/_doc/[ID_CUSTOMIZADO] - define o ID que eu quiser d documento
POST products/_doc/1
{
  "name": "Xuxu",
  "description": "Xuxu beleza"
}

# http://localhost:9200/[INDICE]/_count
GET usuarios/_count

# http://localhost:9200/[INDICE]/_doc/[ID] - busca documento por ID
GET products/_doc/1

# http://localhost:9200/[INDICE]/_search - busca todos os documentos
GET products/_search

# http://localhost:9200/[INDICE]/_search?q=banana - busca todos os documentos com termo
GET products/_search?q=banana

# http://localhost:9200/[INDICE]/_doc/[ID] - confirma se o registro existe
HEAD products/_doc/1

# http://localhost:9200/[INDICE]/_doc/[ID] - atualia registro pelo ID
PUT products/_doc/1
{
  "name" : "Xuxu",
  "description" : "Xuxu beleza yeah yeah"
}

# http://localhost:9200/[INDICE]/_doc/[ID] - apaga registro pelo ID
DELETE products/_doc/1

# http://localhost:9200/[INDICE]/_search?q=description:nanica - busca por termo com campo específico
GET products/_search?q=description:nanica


# http://localhost:9200/_search?size=2 - [PAGINAÇÃO] padrão para busca geral = 10, alterando para 10
GET _search?size=2
# http://localhost:9200/_search - [PAGINAÇÃO] acessando pagina 2 de busca com padrão de 10 em 10
GET _search?from=1
# http://localhost:9200/[INDICE]/_search?size=2 - [PAGINAÇÃO] busca paginado por 2
GET products/_search?size=2
# http://localhost:9200/[INDICE]/_search?size=2&from=2 - [PAGINAÇÃO] busca paginado por 2, pela pagina 3
GET products/_search?size=2&from=2
# http://localhost:9200/[INDICE]/_search?size=2&from=0 - [PAGINAÇÃO] busca para primeira pagina, paginado por 2 itens
GET products/_search?size=2&from=0 

# http://localhost:9200/[INDICE]/_mapping - mostra a descrição do documento, igual a um 'desc table' do SQL
GET products/_mapping

# http://localhost:9200/_reindex - renomear index
POST /_reindex
{
  "source": {
    "index": "catalogo"
  },
  "dest": {
    "index": "pessoas"
  }
}

# http://localhost:9200/[INDICE] - apagar index
DELETE /catalogo


# ====== trabalhando com indice sentitive case e sem acentos ======
DELETE /my_index

PUT /my_index
{
  "settings": {
    "analysis": {
      "analyzer": {
        "folding": {
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "asciifolding"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "text": {
        "type": "text",
        "analyzer": "folding",
        "fields": {
          "keyword": {
            "type": "keyword",
            "ignore_above": 256
          }
        }
      }
    }
  }
}
}

POST my_index/_doc/1
{"text":"olá"}

GET /my_index/_search?q=ola

GET /my_index/_search
{
  "query": {
    "query_string": {
      "query": "ola"
    }
  }
}

# ====== definindo tipo antes de criar o documento ======
# http://localhost:9200/[INDICE]/_mapping - altera tipo de um campo
PUT products/_mapping
{
    "properties": {
        "idade": {
            "type": "float"
        }
    }
}

POST products/_doc
{
  "name" : "Lenovo",
  "description" : "Lenovo Laptops",
  "idade": "20",
}

GET products/_mapping

# ====== Busca por range, maior que menor que (idade) gte(maior ou igual que), lte(menor ou igual que)======
https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-range-query.html
GET pessoas/_search
{
  "query": {
    "range": {
      "idade": {
        "gte": 10,
        "lte": 20
      }
    }
  }
}

# ====== Busca por range, maior que menor que (data) gte(maior que), lte(menor que)======
https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-range-query.html
POST pessoas/_doc
{
  "nome": "Daniel",
  "data": "2022-04-20T10:00:00"
}

GET pessoas/_search
{
  "query": {
    "range": {
      "data": {
        "gte": "2022-04-20T00:00:00",
        "lte": "2022-04-30T23:59:59"
      }
    }
  }
}

GET pessoas/_search
{
  "query": {
    "range": {
      "data": {
        "time_zone": "+01:00",        
        "gte": "2022-04-20T00:00:00", 
        "lte": "now"                  
      }
    }
  }
}


# ====== trabalhando com did you mean ======
DELETE /movies-titles

PUT movies-titles
{
  "settings": {
    "index": {
      "number_of_shards": 1,
      "analysis": {
        "analyzer": {
          "trigram": {
            "type": "custom",
            "tokenizer": "standard",
            "filter": ["lowercase", "shingle"]
          }
        },
        "filter": {
          "shingle": {
            "type": "shingle",
            "min_shingle_size": 2,
            "max_shingle_size": 3
          }
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "title": {
        "type": "text",
        "fields": {
          "trigram": {
            "type": "text",
            "analyzer": "trigram"
          }
        }
      }
    }
  }
}

# Analise de dados que você poderá buscar
GET movies-titles/_analyze
{
  "field": "title.trigram",
  "text": "please divide this sentence into shingles"
}

# exeplos de buscas que ele irá encontrar
["please", "please divide", "please divide this", "divide", "divide this", "divide this sentence", "this", "this sentence", "this sentence into", "sentence", "sentence into", "sentence into shingles", "into", "into shingles", "shingles"]

# cadastrando itens em movies
POST movies/_doc
{
  "title": "Os amigos dos outros"
}
POST movies/_doc
{
  "title": "O rato de romeu"
}
POST movies/_doc
{
  "title": "Matrix revolution"
}
POST movies/_doc
{
  "title": "O vento levou"
}
POST movies/_doc
{
  "title": "star wars"
}

# reindexando indices
POST _reindex
{
  "source": {
    "index": ["movies"],
    "_source": ["title"]
  },
  "dest": {
    "index": "movies-titles"
  }
}

# está busca mostra o exemplo cadastrado no elasticsearch com did you mean em uma busca com texto "ventu"
GET movies-titles/_search
{
  "suggest": {
    "text": "ventu",
    "did_you_mean": {
      "phrase": {
        "field": "title.trigram",
        "size": 5,
        "direct_generator": [
          {
            "field": "title.trigram"
          }
        ],
        "collate": {
          "query": { 
            "source" : {
              "match": {
                "{{field_name}}": {
                  "query": "{{suggestion}}",
                  "operator": "and"
                }
              }
            }
          },
          "params": {"field_name" : "title"}
        },
        "highlight": {
          "pre_tag": "<strong>",
          "post_tag": "</strong>"
        }
      }
    }
  }
}

```


# criando projeto
mvn archetype:generate -DgroupId=br.com.elasticsearch -DartifactId=elasticsearch -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

# doc mvn
https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
### gerar com manifest
https://www.sohamkamani.com/java/cli-app-with-maven/

# como rodar
```shell
./buld.sh
./start.sh
```

# Install on maven
- https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/installation.html

# dependencias
```xml
<project>
  <dependencies>

   <dependency>
      <groupId>co.elastic.clients</groupId>
      <artifactId>elasticsearch-java</artifactId>
      <version>8.1.3</version>
    </dependency>

    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.13.2.2</version>
    </dependency>

    <!-- Needed only if you use the spring-boot Maven plugin -->
    <dependency> 
      <groupId>jakarta.json</groupId>
      <artifactId>jakarta.json-api</artifactId>
      <version>2.0.1</version>
    </dependency>

    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.9</version>
    </dependency>

  </dependencies>
</project>
```
