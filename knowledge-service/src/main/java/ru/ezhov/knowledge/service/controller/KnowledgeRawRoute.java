package ru.ezhov.knowledge.service.controller;

import org.eclipse.jetty.util.UrlEncoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import java.net.URLEncoder;
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
            String url = URLDecoder
                    .decode(request.queryParams("raw"), StandardCharsets.UTF_8.name());
            logger.debug("получена ссылка для сырых данных: {}", url);
            String urlDecode = URLEncoder.encode(url, "UTF-8");
            logger.debug("ссылка декодирована: {}", urlDecode);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(urlDecode).openConnection();
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
            response.type("text/plain");
            return stringBuilder.toString();
        }
    }
}
