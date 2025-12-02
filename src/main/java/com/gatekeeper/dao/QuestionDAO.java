package com.gatekeeper.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gatekeeper.DatabaseConnector;
import com.gatekeeper.model.Option;
import com.gatekeeper.model.Question;

public class QuestionDAO {

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        
        String sql = "SELECT q.id, q.question_text, q.weight, c.name " +
                     "FROM questions q " +
                     "JOIN categories c ON q.category_id = c.id";

        try(Connection conn = DatabaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()){
                int id = rs.getInt("id");
                String text = rs.getString("question_text");
                int weight = rs.getInt("weight");
                String category = rs.getString("name");

                Question q = new Question(id, category, text, weight);

                List<Option> loadedOptions = getOptionsByQuestionId(q.getID());

                for (Option op : loadedOptions) {
                    q.addOption(op);
                }

                questions.add(q);
            }
            
        } catch (SQLException e) {
            System.out.println("Fehler beim Laden der Fragen: " + e.getMessage());
            e.printStackTrace();
        }

        return questions;
    }

    private List<Option> getOptionsByQuestionId(int questionId) {
        List<Option> options = new ArrayList<>();

        String sql = "SELECT * FROM options WHERE question_id = ?";

        try (Connection conn = DatabaseConnector.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, questionId);

            try(ResultSet rs = pstmt.executeQuery()) {

                while(rs.next()){
                    int id = rs.getInt("id");
                    int score = rs.getInt("score_value");
                    String text = rs.getString("option_text");

                    Option o = new Option(id, score, text);
                    options.add(o);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }
}
