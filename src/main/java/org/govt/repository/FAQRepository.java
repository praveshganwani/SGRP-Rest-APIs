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
import org.govt.model.FAQs;
import org.govt.model.Status;

/**
 *
 * @author Pravesh Ganwani
 */
public class FAQRepository {
    public List<FAQs> getFAQs() {
        List<FAQs> faqs = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from faqs");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                FAQs f = new FAQs();
                f.setFaqId(rs.getInt("faq_id"));
                f.setFaqKeywords(rs.getString("faq_keywords"));
                f.setFaqTitle(rs.getString("faq_title"));
                f.setFaqDetails(rs.getString("faq_details"));
                f.setFaqComment(rs.getString("faq_comment"));
                f.setCommitteeId(rs.getString("committee_id"));
                faqs.add(f);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return faqs;
    }
    
    public List<FAQs> getFAQ(String id) {
        List<FAQs> faqs = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from faqs where committee_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                FAQs f = new FAQs();
                f.setFaqId(rs.getInt("faq_id"));
                f.setFaqKeywords(rs.getString("faq_keywords"));
                f.setFaqTitle(rs.getString("faq_title"));
                f.setFaqDetails(rs.getString("faq_details"));
                f.setFaqComment(rs.getString("faq_comment"));
                f.setCommitteeId(rs.getString("committee_id"));
                faqs.add(f);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return faqs;
    }
    
    public Status createFAQ(FAQs f) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into faqs (faq_keywords, faq_title, faq_details, faq_comment, committee_id) values(?,?,?,?,?)");
            ps.setString(1, f.getFaqKeywords());
            ps.setString(2, f.getFaqTitle());
            ps.setString(3, f.getFaqDetails());
            ps.setString(4, f.getFaqComment());
            ps.setString(5, f.getCommitteeId());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
//    public Status updateFAQ(FAQs c) {
//        Status st = null;
//        try {
//            Connection con = DBConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement("update courses set course_name=?, institute_id=? where course_id=?");
//            ps.setString(1, c.getFAQName());
//            ps.setString(2, c.getInstituteId());
//            ps.setInt(3, c.getFAQId());
//            ps.executeUpdate();
//            st = new Status();
//            st.setStatus(1);
//            con.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return st;
//    }
    
    public Status deleteFAQ(int id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from faqs where faq_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
}
