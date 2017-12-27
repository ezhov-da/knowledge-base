package ru.ezhov.knowledge.service.view;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class KnowledgesAllJsonAnswerTest {
    @Test
    public void getLastUpdate() throws Exception {
        KnowledgesAllJsonAnswer knowledgesAllJsonAnswer =
                new KnowledgesAllJsonAnswer(new Date(), null);

        System.out.println(knowledgesAllJsonAnswer.getLastUpdate());
    }

}