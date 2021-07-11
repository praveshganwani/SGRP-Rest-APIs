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
import org.govt.configs.DBConfig;
import org.govt.model.Activity;
import org.govt.model.Status;

/**
 *
 * @author Pravesh Ganwani
 */
public class ActivityRepository {
    public List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from activities");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Activity a = new Activity();
                a.setActivityId(rs.getInt("activity_id"));
                a.setComplaintId(rs.getString("complaint_id"));
                a.setActivityTime(rs.getString("activity_time"));
                a.setActivityType(rs.getString("activity_type"));
                a.setActivityComment(rs.getString("activity_comment"));
                a.setActivityFrom(rs.getString("activity_from"));
                a.setActivityTo(rs.getString("activity_to"));
                activities.add(a);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return activities;
    }
    
    public List<Activity> getActivity(String id) {
        List<Activity> activities = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from activities where complaint_id=? ORDER BY activity_time DESC");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Activity a = new Activity();
                a.setActivityId(rs.getInt("activity_id"));
                a.setComplaintId(rs.getString("complaint_id"));
                a.setActivityTime(rs.getString("activity_time"));
                a.setActivityType(rs.getString("activity_type"));
                a.setActivityComment(rs.getString("activity_comment"));
                a.setActivityFrom(rs.getString("activity_from"));
                a.setActivityTo(rs.getString("activity_to"));
                activities.add(a);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return activities;
    }
    
    public List<Activity> getAscendingActivity(String id) {
        List<Activity> activities = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from activities where complaint_id=? ORDER BY activity_time ASC");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Activity a = new Activity();
                a.setActivityId(rs.getInt("activity_id"));
                a.setComplaintId(rs.getString("complaint_id"));
                a.setActivityTime(rs.getString("activity_time"));
                a.setActivityType(rs.getString("activity_type"));
                a.setActivityComment(rs.getString("activity_comment"));
                a.setActivityFrom(rs.getString("activity_from"));
                a.setActivityTo(rs.getString("activity_to"));
                activities.add(a);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return activities;
    }
    
    public Status createActivity(Activity a) {
        Status st = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = sdf.format(dt);
        a.setActivityTime(finalDate);
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into activities (complaint_id, activity_time, activity_type, activity_comment, activity_from, activity_to) values(?,?,?,?,?,?)");
            ps.setString(1, a.getComplaintId());
            ps.setString(2, a.getActivityTime());
            ps.setString(3, a.getActivityType());
            ps.setString(4, a.getActivityComment());
            ps.setString(5, a.getActivityFrom());
            ps.setString(6, a.getActivityTo());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
//    public Status updateActivity(Activity c) {
//        Status st = null;
//        try {
//            Connection con = DBConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement("update courses set course_name=?, institute_id=? where course_id=?");
//            ps.setString(1, c.getActivityName());
//            ps.setString(2, c.getInstituteId());
//            ps.setInt(3, c.getActivityId());
//            ps.executeUpdate();
//            st = new Status();
//            st.setStatus(1);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return st;
//    }
    
    public Status deleteActivity(int id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from activities where activity_id=?");
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
