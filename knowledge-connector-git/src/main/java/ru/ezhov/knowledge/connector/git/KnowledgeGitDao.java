package ru.ezhov.knowledge.connector.git;

import ru.ezhov.knowledge.common.Knowledge;
import ru.ezhov.knowledge.common.KnowledgeDao;
import ru.ezhov.knowledge.common.PropertiesHolder;

import java.util.List;

public class KnowledgeGitDao implements KnowledgeDao {
    private String token;
    private String user;

    public KnowledgeGitDao(String token, String user) {
        this.token = token;
        this.user = user;
    }

    @Override
    public List<Knowledge> getKnowledges() throws Exception {
        GitClient gitClient = new GitClient(token, user);
        return gitClient.getKnowledgeTransforms(new GitToKnowledgeConverter());
    }
}
