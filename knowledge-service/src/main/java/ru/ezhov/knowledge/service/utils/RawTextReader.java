package ru.ezhov.knowledge.service.utils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class RawTextReader {
    private String url;

    public RawTextReader(String url) {
        this.url = url;
    }

    public String read() throws Exception {
        StringBuilder stringBuilder;
        URI uri = new URI(url);
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
        return stringBuilder.toString();
    }
}
