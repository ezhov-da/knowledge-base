package ru.ezhov.knowledge.service.view;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class KnowledgeTreeContextTest {

    @Test
    public void from() {
        List<KnowledgeClient> knowledgeClients = Arrays.asList(
                new KnowledgeClient("-", "-java-Test", "", "", "", true),
                new KnowledgeClient("-", "-java-maven-Test", "", "", "", true),
                new KnowledgeClient("-", "-java-maven-gradle-Test", "", "", "", true),
                new KnowledgeClient("-", "-bat-Test", "", "", "", true)
        );

        KnowledgeTreeContextNode knowledgeTreeContextNode = KnowledgeTreeContext.from(knowledgeClients);
    }
}