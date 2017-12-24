package ru.ezhov.knowledge.connector.git;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.junit.Ignore;
import org.junit.Test;
import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.propertiesReader.Properties;
import ru.ezhov.propertiesReader.PropertiesFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.*;

@Ignore
public class GitClientTest {
    @Test
    public void getKnowledgeTransforms() throws Exception {
        Properties<String, String> stringProperties = PropertiesFactory.getPropertiesFromUserDirectory("common.properties");

        GitClient gitClient = new GitClient(
                stringProperties.getProperty("git.token"),
                stringProperties.getProperty("git.user")
        );

        try {
            List<Knowledge> knowledgeTransforms = gitClient.getKnowledgeTransforms(new GitToKnowledgeConverter());
            System.out.println(knowledgeTransforms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFiles(Gist gist) throws Exception {
        Map<String, GistFile> stringFileMap = gist.getFiles();
        stringFileMap.forEach((s, f) -> {
//            System.out.println("s: " + s);
//            System.out.println("url: " + f.getRawUrl());
//            System.out.println("name: " + f.getFilename());

//            HttpURLConnection urlConnection = null;
//            try {
//
//                urlConnection =
//                        (HttpURLConnection) new URL(f.getRawUrl()).openConnection();
//
//                String result = getStringFromInputStream(urlConnection.getInputStream());
//                System.out.println(result);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//            }
        });
    }

    private List<KnowledgeFile> readFilesToList(Gist gist) throws Exception {

        List<KnowledgeFile> knowledgeFiles = new ArrayList<>();

        Map<String, GistFile> stringFileMap = gist.getFiles();
        stringFileMap.forEach((s, f) -> {
//            System.out.println("s: " + s);
//            System.out.println("url: " + f.getRawUrl());
//            System.out.println("name: " + f.getFilename());
            knowledgeFiles.add(new KnowledgeFile(f.getFilename(), f.getRawUrl()));

        });

        return knowledgeFiles;
    }

    // convert InputStream to String

    private String getStringFromInputStream(InputStream is) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(is, "UTF-8");
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString().trim();
    }

}