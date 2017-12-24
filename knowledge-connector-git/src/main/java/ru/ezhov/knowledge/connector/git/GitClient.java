package ru.ezhov.knowledge.connector.git;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.GistService;
import ru.ezhov.knowledge.common.Converter;
import ru.ezhov.knowledge.common.Knowledge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class GitClient {
    private String token;
    private String user;

    public GitClient(String token, String user) {
        this.token = token;
        this.user = user;
    }

    public List<Knowledge> getKnowledgeTransforms(Converter<Knowledge, KnowledgeGit> knowledgeConverter)
            throws Exception {
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(token);
        PageIterator<Gist> gistPageIterator = new GistService(gitHubClient).pageGists(user);
        List<KnowledgeGit> knowledgeGitList = new ArrayList<>();

        while (gistPageIterator.hasNext()) {
            Collection<Gist> gistCollection = gistPageIterator.next();

            for (Gist gist1 : gistCollection) {
                knowledgeGitList.add(createKnowledge(gist1));
            }
        }

        List<Knowledge> knowledgeTransforms = new ArrayList<>();
        knowledgeGitList.forEach(k -> {
            List<Knowledge> transforms = knowledgeConverter.convert(k);
            knowledgeTransforms.addAll(transforms);
        });
        return knowledgeTransforms;
    }

    private KnowledgeGit createKnowledge(Gist gist) throws Exception {
        KnowledgeGit knowledgeGit = new KnowledgeGit(
                gist.getDescription(),
                gist.getHtmlUrl(),
                gist.isPublic(),
                gist.getUpdatedAt()
        );

        knowledgeGit.setKnowledgeFiles(readFilesToList(gist));
        return knowledgeGit;
    }

    private List<KnowledgeFile> readFilesToList(Gist gist) throws Exception {
        List<KnowledgeFile> knowledgeFiles = new ArrayList<>();

        Map<String, GistFile> stringFileMap = gist.getFiles();
        stringFileMap.forEach((s, f) -> {
            knowledgeFiles.add(new KnowledgeFile(f.getFilename(), f.getRawUrl()));

        });

        return knowledgeFiles;
    }
}
