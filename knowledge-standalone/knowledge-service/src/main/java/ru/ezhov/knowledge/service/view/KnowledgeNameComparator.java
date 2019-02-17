package ru.ezhov.knowledge.service.view;


import ru.ezhov.knowledge.common.Knowledge;

import java.text.Collator;
import java.util.Comparator;

public class KnowledgeNameComparator implements Comparator<Knowledge> {
    @Override
    public int compare(Knowledge o1, Knowledge o2) {
        String name1 = o1.getName();
        String name2 = o2.getName();
        Collator collator = Collator.getInstance();
        return collator.compare(name1, name2);
    }
}
