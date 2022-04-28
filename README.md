# elasticsearch
- https://www.elastic.co/pt/

# install elasticsearch
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


# install kibana
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
GET _search
{
  "query":{
    "match_all" : {}
  }
}


POST products/_doc
{
  "name": "Limão",
  "description": "Limão caetano"
}


GET usuarios/_count
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
      <version>2.12.3</version>
    </dependency>

    <!-- Needed only if you use the spring-boot Maven plugin -->
    <dependency> 
      <groupId>jakarta.json</groupId>
      <artifactId>jakarta.json-api</artifactId>
      <version>2.0.1</version>
    </dependency>

  </dependencies>
</project>
```
