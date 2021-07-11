/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.govt.configs.DBConfig;
import org.govt.model.Category;
import org.govt.model.Status;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Pravesh Ganwani
 */
public class CategoryRepository {
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from grievance_category");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setHighCommittee(rs.getString("high_committee"));
                c.setLowCommittee(rs.getString("low_committee"));
                categories.add(c);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return categories;
    }
    
    public Category getCategory(int id) {
        Category c = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from grievance_category where category_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setHighCommittee(rs.getString("high_committee"));
                c.setLowCommittee(rs.getString("low_committee"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }
    
    public Status createCategory(Category c) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from grievance_category where category_name=?");
            ps.setString(1, c.getCategoryName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                st = new Status();
                st.setStatus(-4);
                return st;
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into grievance_category (category_name, high_committee, low_committee) values(?,?,?)");
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getHighCommittee());
            ps.setString(3, c.getLowCommittee());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status updateCategory(Category c) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("update grievance_category set category_name=?, high_committee=?, low_committee=? where category_id=?");
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getHighCommittee());
            ps.setString(3, c.getLowCommittee());
            ps.setInt(4, c.getCategoryId());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status deleteCategory(int id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from grievance_category where category_id=?");
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
