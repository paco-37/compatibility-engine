package com.gatekeeper.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gatekeeper.DatabaseConnector;
import com.gatekeeper.model.Applicant;

public class ApplicantDAO {

    public void saveApplicant(Applicant applicant) {

        String sql = "INSERT INTO applicants (first_name, total_score) VALUES (?, ?)";

        try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, applicant.getFirst_name());
            pstmt.setInt(2, applicant.getTotal_score());

            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Applicant> getTopApplicants() {

        List<Applicant> applicants = new ArrayList<>();

        String sql = "SELECT id, first_name, total_score FROM applicants ORDER BY total_score DESC LIMIT 10";

            try(Connection conn = DatabaseConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {


            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String name = rs.getString("first_name");
                    int score = rs.getInt("total_score");

                    Applicant a = new Applicant(name, score);
                    applicants.add(a);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applicants;

    }
    
}
