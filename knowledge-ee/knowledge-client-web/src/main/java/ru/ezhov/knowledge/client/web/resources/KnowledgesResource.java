package ru.ezhov.knowledge.client.web.resources;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("knowledges.json")
public class KnowledgesResource {

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        Response response = Response.ok(
                Json.createObjectBuilder()
                        .add("lastUpdate", "Вт 19.02.2019 22:44:51")
                        .add("context", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("name", "server")
                                        .add("count", "1")
                                        .build())
                                .build()
                        )
                        .add("knowledges.json", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("type", "server")
                                        .add("name", "1")
                                        .add("rawUrl", "1")
                                        .add("description", "1")
                                        .add("url", "1")
                                        .add("public", "true")
                                        .build())
                                .build()
                        )
                        .build()

        ).build();
        return response;
    }
}
