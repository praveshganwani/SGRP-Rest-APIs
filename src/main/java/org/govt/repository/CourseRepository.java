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
import org.govt.model.Course;
import org.govt.model.Status;

/**
 *
 * @author Pravesh Ganwani
 */
public class CourseRepository {
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from courses");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Course c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCourseName(rs.getString("course_name"));
                c.setInstituteId(rs.getString("institute_id"));
                
                courses.add(c);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return courses;
    }
    
    public Course getCourse(int id) {
        Course c = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from courses where course_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCourseName(rs.getString("course_name"));
                c.setInstituteId(rs.getString("institute_id"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }
    
    public Status createCourse(Course c) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from courses where course_name=? and institute_id=?");
            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getInstituteId());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                st = new Status();
                st.setStatus(-4);
                return st;
            }
            ps = con.prepareStatement("insert into courses(course_name, institute_id) values(?,?)");
            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getInstituteId());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status updateCourse(Course c) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("update courses set course_name=?, institute_id=? where course_id=?");
            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getInstituteId());
            ps.setInt(3, c.getCourseId());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status deleteCourse(int id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from courses where course_id=?");
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
