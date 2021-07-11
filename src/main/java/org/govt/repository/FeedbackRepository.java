/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.govt.configs.DBConfig;
import org.govt.model.Feedback;
import org.govt.model.Status;

/**
 *
 * @author Pravesh Ganwani
 */
public class FeedbackRepository {
    public List<Feedback> getFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from feedbacks");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Feedback f = new Feedback();
                f.setName(rs.getString("name"));
                f.setEmail(rs.getString("email"));
                f.setFeedback(rs.getString("feedback"));
                feedbacks.add(f);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return feedbacks;
    }
    
    public Feedback getFeedback(int id) {
        Feedback f = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from feedbacks where feedback_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                f = new Feedback();
                f.setName(rs.getString("name"));
                f.setEmail(rs.getString("email"));
                f.setFeedback(rs.getString("feedback"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return f;
    }
    
    public Status createFeedback(Feedback f) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into feedbacks (name, email, feedback) values(?,?,?)");
            ps.setString(1, f.getName());
            ps.setString(2, f.getEmail());
            ps.setString(3, f.getFeedback());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
}
