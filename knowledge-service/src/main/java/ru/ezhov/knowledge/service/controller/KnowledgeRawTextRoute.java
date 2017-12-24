package ru.ezhov.knowledge.service.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KnowledgeRawTextRoute implements Route {
    private static String HEAD_PARAM1 = "Access-Control-Allow-Origin";
    private static String HEAD_VALUE1 = "*";
    private static String HEAD_PARAM2 = "charset";
    private static String HEAD_VALUE2 = "utf-8";
    private static String TYPE = "application/json";

    private static Logger LOG = LoggerFactory.getLogger(KnowledgeRawTextRoute.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.header(HEAD_PARAM1, HEAD_VALUE1);
        response.header(HEAD_PARAM2, HEAD_VALUE2);
        response.type(TYPE);

        String hash = request.queryParams("hash");
        LOG.debug("Load raw text from url: {}", hash);

        String returnJson;
        StringBuilder stringBuilder = null;
        try {
            URI uri = new URI(hash);
            HttpURLConnection httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();

            stringBuilder = new StringBuilder();
            try (Scanner scanner = new Scanner(
                    new InputStreamReader(
                            httpURLConnection.getInputStream(),
                            "UTF-8")
            )) {
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine());
                    stringBuilder.append("\n");
                }
            }

            httpURLConnection.disconnect();
            returnJson = createJson(stringBuilder.toString());
        } catch (Exception e) {
            LOG.error("Error read raw url: {}", e);
            returnJson = createJson("Sorry, but raw text can't write...");
        }
        return returnJson;

    }

    private String createJson(String text) throws IOException {
        String resultText = text == null || text.isEmpty() ? "oops" : text;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("text", resultText);
        String json = objectMapper.writeValueAsString(stringStringHashMap);
        LOG.debug("Return json: {}", json);
        return json;
    }
}
