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
import org.govt.model.Response;
import org.govt.model.Status;

/**
 *
 * @author Pravesh Ganwani
 */
public class ResponseRepository {
    public List<Response> getResponses() {
        List<Response> responses = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from responses");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Response r = new Response();
                r.setResponseId(rs.getInt("response_id"));
                r.setComplaintId(rs.getString("complaint_id"));
                r.setResponseTime(rs.getString("response_time"));
                r.setResponse(rs.getString("response"));
                r.setResponseFrom(rs.getString("response_from"));
                r.setResponseTo(rs.getString("response_to"));
                responses.add(r);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return responses;
    }
    
    public List<Response> getResponse(String id) {
        List<Response> responses = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from responses where complaint_id=? ORDER BY response_time DESC");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Response r = new Response();
                r.setResponseId(rs.getInt("response_id"));
                r.setComplaintId(rs.getString("complaint_id"));
                r.setResponseTime(rs.getString("response_time"));
                r.setResponse(rs.getString("response"));
                r.setResponseFrom(rs.getString("response_from"));
                r.setResponseTo(rs.getString("response_to"));
                responses.add(r);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return responses;
    }
    
    public List<Response> getAscendingResponse(String id) {
        List<Response> responses = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from responses where complaint_id=? ORDER BY response_time ASC");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Response r = new Response();
                r.setResponseId(rs.getInt("response_id"));
                r.setComplaintId(rs.getString("complaint_id"));
                r.setResponseTime(rs.getString("response_time"));
                r.setResponse(rs.getString("response"));
                r.setResponseFrom(rs.getString("response_from"));
                r.setResponseTo(rs.getString("response_to"));
                responses.add(r);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return responses;
    }
    
    public Status createResponse(Response r) {
        Status st = null;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = sdf.format(dt);
        r.setResponseTime(finalDate);
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into responses (complaint_id, response_time, response, response_from, response_to) values(?,?,?,?,?)");
            ps.setString(1, r.getComplaintId());
            ps.setString(2, r.getResponseTime());
            ps.setString(3, r.getResponse());
            ps.setString(4, r.getResponseFrom());
            ps.setString(5, r.getResponseTo());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
//    public Status updateResponse(Response c) {
//        Status st = null;
//        try {
//            Connection con = DBConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement("update courses set course_name=?, institute_id=? where course_id=?");
//            ps.setString(1, c.getResponseName());
//            ps.setString(2, c.getInstituteId());
//            ps.setInt(3, c.getResponseId());
//            ps.executeUpdate();
//            st = new Status();
//            st.setStatus(1);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return st;
//    }
    
    public Status deleteResponse(int id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from responses where response_id=?");
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
