package com.biglobby.utils.recommender.contentbased;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentBasedRecommender {

    private Double minScore = 0.0;
    private Integer maxSimilarDocuments = Integer.MAX_VALUE;
    private Boolean debug = false;
    private Map<Long, List<ScoreModel>> data;
    private static ContentBasedRecommender instance;
    private List<DocumentModel> documents;
    private static final Logger logger = LoggerFactory.getLogger(ContentBasedRecommender.class);

    public static synchronized void setInstance(ContentBasedRecommender newInstance) {
        ContentBasedRecommender.instance = newInstance;
    }

    public static synchronized ContentBasedRecommender getInstance() throws Exception {
        if (instance == null) {
            return new ContentBasedRecommender();
        }
        return instance;
    }

    public ContentBasedRecommender() {
        this.data = new HashMap<>();
    }

    public ContentBasedRecommender(Double minScore, Integer maxSimilarDocuments, Boolean debug) throws Exception {
        if (minScore != null && (minScore < 0 || minScore > 1)) {
            throw new Exception("MinScore must be between 0 and 1");
        }
        this.minScore = minScore;

        if (maxSimilarDocuments != null && maxSimilarDocuments <= 0) {
            throw new Exception("MaxSimilarDocuments must be greater than 0");
        }
        this.maxSimilarDocuments = maxSimilarDocuments;

        if (debug != null) {
            this.debug = debug;
        }
        this.data = new HashMap<>();
    }

    public void setDocuments(List<DocumentModel> documents) {
        this.documents = documents;
    }

    public void train() {
        if (this.debug) {
            logger.info("Total documents: " + this.documents.size());
        }
        this.data = this.calculateSimilarityDocuments(this.documents);
    }

    public void reTrain(List<DocumentModel> newDocs) {
        Map<Long, List<ScoreModel>> dataset = calculateSimilarityDocuments(newDocs);
        this.data = dataset;
    }

    private Map<Long, List<ScoreModel>> calculateSimilarityDocuments(List<DocumentModel> documents) {
        Map<Long, List<ScoreModel>> result = new HashMap<>();
        Integer documentSize = documents.size();
        List<ScoreModel> scoresTemp = new ArrayList<ScoreModel>();
        for (int i = 0; i < documentSize; i++) {
            if (this.debug) {
                logger.info("Calculating similarity for document " + documents.get(i).getId());
            }
            for (int j = 0; j < i; j++) {
                double similarity = this.similarity(documents.get(i).getContent(), documents.get(j).getContent());
                ScoreModel score = new ScoreModel();
                score.setScore(similarity);

                if (similarity >= this.minScore) {
                    score.setId(documents.get(j).getId());
                    if (result.containsKey(documents.get(i).getId())) {
                        result.get(documents.get(i).getId()).add(score);
                    } else {
                        scoresTemp.add(score);
                        result.put(documents.get(i).getId(), scoresTemp);
                        scoresTemp.clear();
                    }
                    score.setId(documents.get(i).getId());
                    if (result.containsKey(documents.get(j).getId())) {
                        result.get(documents.get(j).getId()).add(score);
                    } else {
                        scoresTemp.add(score);
                        result.put(documents.get(j).getId(), scoresTemp);
                        scoresTemp.clear();
                    }
                }

            }
        }
        return this.orderDocuments(result);
    }

    public Map<Long, List<ScoreModel>> orderDocuments(Map<Long, List<ScoreModel>> dataset) {
        Set<Long> keys = dataset.keySet();
        for (Long key : keys) {
            List<ScoreModel> scores = dataset.get(key);
            scores.sort((o1, o2) -> o1.getScore().compareTo(o2.getScore()));
            if (scores.size() > this.maxSimilarDocuments) {
                dataset.get(key).clear();
                dataset.get(key).addAll(scores.subList(0, this.maxSimilarDocuments));
            }
        }
        return dataset;
    }

    public List<ScoreModel> getSimilarity(Long id, int start, int size) {
        if (this.debug) {
            logger.info("Get similarity for document: " + id);
        }
        List<ScoreModel> temp = new ArrayList<>();
        if (!this.data.containsKey(id)) {
            return temp;
        }
        temp = this.data.get(id);
        return temp.subList(start, size);
    }

    public double similarity(String text1, String text2) {
        String longer = text1;
        String shorter = text2;
        if (text1.length() < text2.length()) {
            longer = text2;
            shorter = text1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }
        return ((longerLength - editDistance(longer, shorter)) / (double) longerLength) * 100;
    }

    public int editDistance(String text1, String text2) {
        text1 = text1.toLowerCase();
        text2 = text2.toLowerCase();
        int[] costs = new int[text2.length() + 1];
        for (int i = 0; i <= text1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= text2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (text1.charAt(i - 1) != text2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
                costs[text2.length()] = lastValue;
            }
        }
        return costs[text2.length()];
    }

    public List<String> getTokens(String text) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text, " ");
        while (tokenizer.hasMoreElements()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

}
