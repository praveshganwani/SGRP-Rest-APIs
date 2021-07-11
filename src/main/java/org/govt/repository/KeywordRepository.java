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
import org.govt.model.Keyword;
import org.govt.model.Status;

/**
 *
 * @author Pravesh Ganwani
 */
public class KeywordRepository {
    public List<Keyword> getKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from keywords");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Keyword k = new Keyword();
                k.setKeywordId(rs.getInt("keyword_id"));
                k.setKeywordName(rs.getString("keyword_name"));
                k.setComplaintId(rs.getString("complaint_id"));
                keywords.add(k);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return keywords;
    }
    
    public Keyword getKeyword(String id) {
        Keyword k = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from keywords where complaint_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                k = new Keyword();
                k.setKeywordId(rs.getInt("keyword_id"));
                k.setKeywordName(rs.getString("keyword_name"));
                k.setComplaintId(rs.getString("complaint_id"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return k;
    }
    
    public Status createKeyword(Keyword k) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into keywords (keyword_name, complaint_id) values(?,?)");
            ps.setString(1, k.getKeywordName());
            ps.setString(2, k.getComplaintId());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status deleteKeyword(String id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from keywords where complaint_id=?");
            ps.setString(1, id);
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
