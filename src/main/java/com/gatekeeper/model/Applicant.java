package com.gatekeeper.model;

public class Applicant {

    private int id;
    private String first_name;
    private int total_score;


    public Applicant() {}

    public Applicant(String first_name, int total_score) {
        this.first_name = first_name;
        this.total_score = total_score;
    }

    public Applicant(int id, String first_name, int total_score) {
        this.id = id;
        this.first_name = first_name;
        this.total_score = total_score;
    }


    public int getId() {
        return id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public int getTotal_score() {
        return total_score;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }
    @Override
    public String toString() {
        return "Name: " + first_name + "| Score: " + total_score + "%";
    }
    
}
