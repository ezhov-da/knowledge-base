package ru.ezhov.knowledge.service.view;

import org.junit.Test;
import ru.ezhov.knowledge.common.Knowledge;

import static org.junit.Assert.*;

public class KnowledgeHashTest {
    @Test
    public void getHash() throws Exception {
        String expected = "a96b0a2ac9321b79a6edec93761be0da";

        Knowledge knowledge = new Knowledge(
                "test",
                null,
                "wow",
                null,
                false,
                null);

        KnowledgeHash knowledgeHash = new KnowledgeHash(knowledge);
        assertEquals(expected, knowledgeHash.getHash());
    }

}