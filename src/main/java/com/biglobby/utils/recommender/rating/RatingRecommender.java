package com.biglobby.utils.recommender.rating;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RatingRecommender {
    private final String DATASET_FILEPATH = new ClassPathResource("dataset/data.txt").getPath();

    public ResponseEntity<List<RecommendedItem>> recommendsys(Long userId) {
        try {
            File file = new File(DATASET_FILEPATH);
            System.out.println(file.getAbsolutePath());
            DataModel datamodel = new FileDataModel(file);
            LogLikelihoodSimilarity similarity = new LogLikelihoodSimilarity(datamodel);
            NearestNUserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, datamodel);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, neighborhood,
                    similarity);

            List<RecommendedItem> recommendations = recommender.recommend(userId, 3);
            System.out.println(recommender);
            return ResponseEntity.ok(recommendations);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
