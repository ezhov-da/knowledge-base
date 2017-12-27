package ru.ezhov.knowledge;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Test;

import static org.junit.Assert.*;

import ru.ezhov.knowledge.common.PropertiesHolder;
import ru.ezhov.knowledge.service.view.KnowledgesAllJsonAnswer;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.HashMap;
import java.util.Map;

public class IntegrationTest {
    private PropertiesHolder propertiesHolder = new PropertiesHolder();

    @Test
    public void testAll() {
        testGetAllKnowledgeOK();
        testPublicUrlOK();
        testPublicRawOK();
        testPrivateUrlOK();
        testPrivateRawOK();
    }

    private void testGetAllKnowledgeOK() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(propertiesHolder.getURL() + "/knowledges"));

        Response response = target.request().get();

        KnowledgesAllJsonAnswer knowledgesAllJsonAnswer = response.readEntity(KnowledgesAllJsonAnswer.class);
        assertNotNull(knowledgesAllJsonAnswer);
    }

    private void testPublicUrlOK() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target =
                client.target(
                        UriBuilder.fromPath(propertiesHolder.getURL() +
                                "/knowledge/147178bb2ecc8a8706b2db1b2fbb8b60/url")
                );

        Response response = target.request().post(Entity.entity("", MediaType.TEXT_HTML));

        Map map = response.readEntity(Map.class);
        assertTrue(map.containsKey("redirect"));
    }

    private void testPublicRawOK() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(
                UriBuilder.fromPath(propertiesHolder.getURL() +
                        "/knowledge/147178bb2ecc8a8706b2db1b2fbb8b60/raw")
        );

        Response response = target.request().post(Entity.entity("", MediaType.TEXT_HTML));

        Map map = response.readEntity(Map.class);
        assertTrue(map.containsKey("text"));
    }

    private void testPrivateUrlOK() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target =
                client.target(
                        UriBuilder.fromPath(propertiesHolder.getURL() +
                                "/knowledge/66b86c23a72ccbd863c08a8c3d6bdd35/url"));

        Map mapResponse = checkPost(target);
        assertTrue(mapResponse.containsKey("redirect"));
    }

    private void testPrivateRawOK() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target =
                client.target(
                        UriBuilder.fromPath(propertiesHolder.getURL() +
                                "/knowledge/66b86c23a72ccbd863c08a8c3d6bdd35/raw"));

        Map mapResponse = checkPost(target);
        assertTrue(mapResponse.containsKey("text"));
    }

    private Map<String, String> checkPost(ResteasyWebTarget target) {
        Map<String, String> map = new HashMap<>();
        map.put("password", propertiesHolder.getPassword());
        Response response = target
                .queryParam("password", propertiesHolder.getPassword())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(map));

        Map mapResponse = response.readEntity(Map.class);
        return mapResponse;
    }
}
