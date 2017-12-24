package ru.ezhov.knowledge.service.view;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import ru.ezhov.knowledge.common.Knowledge;

public class KnowledgeHash {
    private Knowledge knowledge;

    public KnowledgeHash(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public String getHash() {
        HashFunction hf = Hashing.hmacMd5("ezhov".getBytes());
        Hasher hasher = hf.newHasher();
        HashCode hashCode = hasher
                .putString(knowledge.getName() + knowledge.getDescription(), Charsets.UTF_8)
                .hash();
        return hashCode.toString();
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }
}
