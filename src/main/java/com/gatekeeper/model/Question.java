package com.gatekeeper.model;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int id;
    private String questionText;
    private int weight;
    private String category;

    private List<Option> options = new ArrayList<>();

    public Question() {
        
    }

    public Question(int id, String category, String questionText, int weight) {
        this.id = id;
        this.category = category;
        this.questionText = questionText;
        this.weight = weight;
    }
    
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void addOption(Option option){
        options.add(option);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question{");
        sb.append("ID=").append(id);
        sb.append(", questionText=").append(questionText);
        sb.append(", weight=").append(weight);
        sb.append(", category=").append(category);
        sb.append(options);
        sb.append('}');
        return sb.toString();
    }

}
