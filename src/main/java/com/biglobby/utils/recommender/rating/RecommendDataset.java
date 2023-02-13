package com.biglobby.utils.recommender.rating;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.biglobby.entity.RecommendDataModel;
import com.biglobby.entity.Review;
import com.biglobby.repository.ReviewRepository;

@Component
public class RecommendDataset {

    @Autowired
    private ReviewRepository reviewRepo;

    private Map<Long, RecommendDataModel> dataset;

    private final String DATASET_FILEPATH = new ClassPathResource("dataset/data.txt").getPath();

    private File dataFile;

    public RecommendDataset() {
        this.dataFile = new File(DATASET_FILEPATH);
        if (!this.dataFile.exists()) {
            this.dataFile.mkdir();
        }
    }

    public void writeToFile() {
        try {
            FileWriter fw = new FileWriter(this.dataFile, false);
            for (Long row : dataset.keySet()) {
                RecommendDataModel data = dataset.get(row); 
                fw.write(data.getUserId() + "," + data.getProductId() + "," + data.getRate());
                fw.write("\n");
            }
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Long, RecommendDataModel> getDataset() {
        dataset = new HashMap<Long, RecommendDataModel>();
        List<Review> reviews = reviewRepo.findAll();
        for (Review review : reviews) {
            RecommendDataModel data = new RecommendDataModel();
            data.setUserId(review.getUser().getId());
            data.setProductId(review.getProduct().getId());
            data.setRate(review.getRate());
            this.dataset.put(review.getId(), data);
        }
        this.writeToFile();
        return this.dataset;
    }

    public void addRow(Long reviewId, RecommendDataModel data) throws Exception {
        if (dataset.containsKey(reviewId) || dataset.containsValue(data)) {
            throw new Exception("Id or value already exist!");
        }
        dataset.put(reviewId, data);
        this.addLineToFile(data);
    }

    public void updateRow(Long id, RecommendDataModel data) throws Exception {
        if (!dataset.containsKey(id)) {
            throw new Exception("Id does not exist!");
        }
        dataset.put(id, data);
        this.writeToFile();
    }

    public void removeRow(Long id) throws Exception {
        if (!dataset.containsKey(id)) {
            throw new Exception("Id does not exist!");
        }
        dataset.remove(id);
        this.writeToFile();
    }

    public void addLineToFile(RecommendDataModel data) {
        try {
            FileWriter fw = new FileWriter(this.dataFile, true); 
            fw.write(data.getUserId() + "," + data.getProductId() + "," + data.getRate());
            fw.write("\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
