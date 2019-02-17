package ru.ezhov.knowledge.service.view;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

@Ignore
public class KnowledgesAllJsonAnswerTest {
    @Test
    public void getLastUpdate() throws Exception {
        KnowledgesAllJsonAnswer knowledgesAllJsonAnswer =
                new KnowledgesAllJsonAnswer(new Date(), null);

        System.out.println(knowledgesAllJsonAnswer.getLastUpdate());
    }

}