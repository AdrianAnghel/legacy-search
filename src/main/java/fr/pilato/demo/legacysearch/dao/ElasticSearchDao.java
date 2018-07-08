package fr.pilato.demo.legacysearch.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pilato.demo.legacysearch.domain.Person;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import restx.factory.Component;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by Frank on 7/9/2018.
 */
@Component
public class ElasticSearchDao {

    private RestHighLevelClient esClient;
    private ObjectMapper mapper;

    @Inject
    public ElasticSearchDao(){
        this.mapper=mapper;
        this.esClient = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://127.0.0.1:9200")));
    }

    public void save(Person person) throws IOException {
        byte[] jsonPerson = mapper.writeValueAsBytes(person);
        esClient.index(new IndexRequest("person","doc",person.idAsString()).source(jsonPerson, XContentType.JSON));
    }

    public void delete(String id) throws IOException {
        esClient.delete(new DeleteRequest("person","doc", id));

    }
}
