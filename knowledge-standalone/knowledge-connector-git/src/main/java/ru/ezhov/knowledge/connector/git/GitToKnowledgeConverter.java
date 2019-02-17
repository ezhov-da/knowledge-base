package ru.ezhov.knowledge.connector.git;

import ru.ezhov.knowledge.common.Converter;
import ru.ezhov.knowledge.common.Knowledge;

import java.util.ArrayList;
import java.util.List;

class GitToKnowledgeConverter implements Converter<Knowledge, KnowledgeGit> {
    @Override
    public List<Knowledge> convert(KnowledgeGit knowledgeGit) {
        List<Knowledge> knowledgeTransforms = new ArrayList<>();

        List<KnowledgeFile> knowledgeFiles = knowledgeGit.getKnowledgeFiles();
        if (!knowledgeFiles.isEmpty()) {
            knowledgeFiles.forEach(kf -> knowledgeTransforms.add(
                    new Knowledge(
                            kf.getName(),
                            kf.getRawUrl(),
                            knowledgeGit.getDescription(),
                            knowledgeGit.getUrl(),
                            knowledgeGit.isPublic(),
                            knowledgeGit.getDate()
                    )
            ));

        }
        return knowledgeTransforms;
    }
}
