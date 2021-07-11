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
import org.govt.model.Status;
import org.govt.model.Student;
import org.govt.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Pravesh Ganwani
 */
public class StudentRepository {
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from students");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Student s = new Student();
                s.setStudentId(rs.getString("student_id"));
                s.setStudentFirstName(rs.getString("student_first_name"));
                s.setStudentMiddleName(rs.getString("student_middle_name"));
                s.setStudentLastName(rs.getString("student_last_name"));
                s.setStudentEmail(rs.getString("student_email_id"));
                s.setStudentPassword(rs.getString("student_password"));
                s.setStudentUID(rs.getString("student_uid"));
                s.setCourseId(rs.getInt("course_id"));
                s.setStudentRegistrationDate(rs.getString("student_reg_datetime"));
                s.setIsVerified(rs.getInt("student_isverified"));
                s.setIsActive(rs.getInt("student_isactive"));
                s.setInstituteId(rs.getString("institute_id"));
                s.setStudentMobileNo(rs.getString("student_mobileno"));
                students.add(s);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return students;
    }
    
    public Student getStudent(String id) {
        Student s = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from students where student_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                s = new Student();
                s.setStudentId(rs.getString("student_id"));
                s.setStudentFirstName(rs.getString("student_first_name"));
                s.setStudentMiddleName(rs.getString("student_middle_name"));
                s.setStudentLastName(rs.getString("student_last_name"));
                s.setStudentEmail(rs.getString("student_email_id"));
                s.setStudentPassword(rs.getString("student_password"));
                s.setStudentUID(rs.getString("student_uid"));
                s.setCourseId(rs.getInt("course_id"));
                s.setStudentRegistrationDate(rs.getString("student_reg_datetime"));
                s.setIsVerified(rs.getInt("student_isverified"));
                s.setIsActive(rs.getInt("student_isactive"));
                s.setInstituteId(rs.getString("institute_id"));
                s.setStudentMobileNo(rs.getString("student_mobileno"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return s;
    }
    
    public Status createStudent(Student s) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from students where student_email_id=?");
            ps.setString(1, s.getStudentEmail());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                st = new Status();
                st.setStatus(-4);
                con.close();
                return st;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = sdf.format(dt);
        s.setStudentRegistrationDate(finalDate);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        s.setStudentPassword(bCryptPasswordEncoder.encode(s.getStudentPassword()));
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM students");
            ResultSet rs = ps.executeQuery();
            rs.next();
            String numbers = "123456789";
            Random generator = new Random();
            int randomIndex = generator.nextInt(numbers.length());
            int randomNumber = numbers.charAt(randomIndex);
            StringBuilder randomCharacters = new StringBuilder();
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            for(int i=0; i<4; i++)
            {
                Random characterGenerator = new Random();
                randomIndex = characterGenerator.nextInt(characters.length());
                randomCharacters.append(characters.charAt(randomIndex));
            }
            int totalRows = rs.getInt("COUNT(*)")+1;
            randomNumber = randomNumber*totalRows*23;
            String finalId = "STUD" + randomNumber + randomCharacters;
            s.setStudentId(finalId);
            ps = con.prepareStatement("insert into students (student_id, student_first_name, student_middle_name, student_last_name, student_email_id, student_password, student_uid, course_id, student_reg_datetime, student_isverified, student_isactive, institute_id, student_mobileno) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, s.getStudentId());
            ps.setString(2, s.getStudentFirstName());
            ps.setString(3, s.getStudentMiddleName());
            ps.setString(4, s.getStudentLastName());
            ps.setString(5, s.getStudentEmail());
            ps.setString(6, s.getStudentPassword());
            ps.setString(7, s.getStudentUID());
            ps.setInt(8, s.getCourseId());
            ps.setString(9, s.getStudentRegistrationDate());
            ps.setInt(10, s.getIsVerified());
            ps.setInt(11, s.getIsActive());
            ps.setString(12, s.getInstituteId());
            ps.setString(13, s.getStudentMobileNo());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
//    public void updateStudent(Student a) {
//        try {
//            Connection con = DBConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement("update students set student_first_name=?, student_middle_name=?, student_last_name=?, student_email_id=?, student_password=?, student_uid=?, course_id=?, student_reg_datetime=?, student_isverified=?, student_isactive=?, institute_id=? where student_id=?");
//            ps.setString(1, s.getStudentName());
//            ps.setString(2, s.getStudentEmailId());
//            ps.setString(3, a.getStudentPassword());
//            ps.setString(4, a.getStudentId());
//            ps.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    
    public Status deleteStudent(String id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from students where student_id=?");
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
    
    public String studentLogin(User u) {
        String status = "0"; //start
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from students where student_email_id=?");
            ps.setString(1, u.getUserEmail());
            ResultSet rs = ps.executeQuery();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(rs.next()){
                if(bCryptPasswordEncoder.matches(u.getUserPassword(), rs.getString("student_password")))
                {
                    if(rs.getInt("student_isactive") == 1) {
                        if(rs.getInt("student_isverified") == 1)
                        {
                            status = rs.getString("student_id"); //Logged in
                        }
                        else
                        {
                            status = "-2"; // User not verified
                        }
                    }
                    else {
                        status = "-100"; //Blocked
                    }
                }
                else
                {
                    status = "-1"; // Wrong Password
                }
            }
            else {
                status = "-3"; // User not registered
            }
            con.close();
        } catch (Exception e) {
        }
        return status;
    }
    
    public Status verifyStudent(Student s) {
        Status st = null;
        Student student = getStudent(s.getStudentId());
        if(s.getInstituteId().equals(student.getInstituteId())) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update students set student_isverified=? where student_id=?");
                ps.setInt(1, 1);
                ps.setString(2, s.getStudentId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(0); 
        }
        return st;
    }
    
    public Status blockStudent(Student s) {
        Status st = null;
        Student student = getStudent(s.getStudentId());
        if(s.getInstituteId().equals(student.getInstituteId())) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update students set student_isactive=? where student_id=?");
                ps.setInt(1, 0);
                ps.setString(2, s.getStudentId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(0); 
        }
        return st;
    }
    
    public Status unblockStudent(Student s) {
        Status st = null;
        Student student = getStudent(s.getStudentId());
        if(s.getInstituteId().equals(student.getInstituteId())) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update students set student_isactive=? where student_id=?");
                ps.setInt(1, 1);
                ps.setString(2, s.getStudentId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(0); 
        }
        return st;
    }
}
