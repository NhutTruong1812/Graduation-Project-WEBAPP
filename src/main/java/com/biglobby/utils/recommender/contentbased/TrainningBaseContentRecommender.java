package com.biglobby.utils.recommender.contentbased;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.biglobby.entity.Post;
import com.biglobby.repository.PostRepository;

@Component
public class TrainningBaseContentRecommender {

    @Autowired
    private PostRepository postRepo;

    // @EventListener(ApplicationReadyEvent.class)
    @PostConstruct
    public void doSomethingAfterStartup() {
        List<Post> posts = postRepo.findAll();
        List<DocumentModel> documents = new ArrayList<>();
        Integer postsSize = posts.size();
        for (int i = 0; i < postsSize; i++) {
            DocumentModel doc = new DocumentModel();
            doc.setId(posts.get(i).getId());
            doc.setContent(posts.get(i).getContent());
            documents.add(doc);
        }

        try {
            ContentBasedRecommender recommender = new ContentBasedRecommender(0.0, Integer.MAX_VALUE, true);
            ContentBasedRecommender.setInstance(recommender);
            ContentBasedRecommender.getInstance().setDocuments(documents);
            ContentBasedRecommender.getInstance().train();
            // List<ScoreModel> rec = ContentBasedRecommender.getInstance().getSimilarity(6l, 0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Scheduled(fixedDelay = (1000 * 60 * 10))
    public void reTrainContentBase() {
        List<Post> posts = postRepo.findAll();
        List<DocumentModel> documents = new ArrayList<>();
        Integer postsSize = posts.size();
        for (int i = 0; i < postsSize; i++) {
            DocumentModel doc = new DocumentModel();
            doc.setId(posts.get(i).getId());
            doc.setContent(posts.get(i).getContent());
            documents.add(doc);
        }
        try {
            ContentBasedRecommender.getInstance().reTrain(documents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
