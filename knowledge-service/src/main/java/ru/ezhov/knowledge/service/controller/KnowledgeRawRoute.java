package ru.ezhov.knowledge.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class KnowledgeRawRoute implements Route {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeAllRoute.class);
    private static String HEAD_PARAM = "Access-Control-Allow-Origin";
    private static String HEAD_VALUE = "*";
    private static String TYPE_APPLICATION_JSON = "application/json";
    private static String TYPE_PLANE_TEXT = "text/plane";

    @Override
    public Object handle(Request request, Response response) throws Exception {
        logger.debug("получение знаний методом: {}", request.requestMethod());
        if ("OPTIONS".equals(request.requestMethod())) {
            response.header("Access-Control-Allow-Headers", "x-requested-with");
            response.header("Access-Control-Allow-Method", "GET");
            response.header(HEAD_PARAM, HEAD_VALUE);
            response.type(TYPE_PLANE_TEXT);
            return "OK";
        } else {
            response.header(HEAD_PARAM, HEAD_VALUE);
            String url = new String(Base64.getDecoder().decode(request.queryParams("raw").getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            logger.debug("получена ссылка для сырых данных: {}", url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0)");
            InputStream is;
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = httpURLConnection.getInputStream();
            } else {
                is = httpURLConnection.getErrorStream();
            }
            StringBuilder stringBuilder = new StringBuilder();
            try (Scanner scanner = new Scanner(is, "UTF-8")) {
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine()).append("\n");
                }
            }
            response.status(200);
            response.type("text/plain; charset=utf-8");
            return stringBuilder.toString();
        }
    }
}
