package ru.ezhov.knowledge.service.view;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class KnowledgeTreeContextTest {

    @Test
    public void from() throws Exception {
        List<KnowledgeClient> knowledgeClients = Arrays.asList(
                new KnowledgeClient("-", "-java-Test", "", "", "", true),
                new KnowledgeClient("-", "-java-Test1", "", "", "", true),
                new KnowledgeClient("-", "-java-maven-Test", "", "", "", true),
                new KnowledgeClient("-", "-java-maven-gradle-Test", "", "", "", true),
                new KnowledgeClient("-", "-java-all-Test", "", "", "", true),
                new KnowledgeClient("-", "-sql-common-Test", "", "", "", true),
                new KnowledgeClient("-", "-python-Test", "", "", "", true),
                new KnowledgeClient("-", "-python-Test1", "", "", "", true),
                new KnowledgeClient("-", "-common-jbake-Test1", "", "", "", true),
                new KnowledgeClient("-", "-common-test-Test1", "", "", "", true),
                new KnowledgeClient("-", "-bat-Test", "", "", "", true)
        );
        KnowledgeTreeContextNode knowledgeTreeContextNode = KnowledgeTreeContext.from(knowledgeClients);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(knowledgeTreeContextNode));
    }
}