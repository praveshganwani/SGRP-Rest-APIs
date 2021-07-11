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
import org.govt.model.Committee;
import org.govt.model.Status;
import org.govt.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Pravesh Ganwani
 */
public class CommitteeRepository {
    public List<Committee> getCommittees() {
        List<Committee> committees = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from committees");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Committee c = new Committee();
                c.setCommitteeId(rs.getString("committee_id"));
                c.setCommitteeName(rs.getString("committee_name"));
                c.setCommitteeType(rs.getString("committee_type"));
                c.setCommitteeEmail(rs.getString("committee_email_id"));
                c.setCommitteePassword(rs.getString("committee_password"));
                c.setCommitteeRegistrationDate(rs.getString("committee_reg_datetime"));
                c.setIsVerified(rs.getInt("committee_isverified"));
                c.setIsActive(rs.getInt("committee_isactive"));
                c.setParentId(rs.getString("parent_id"));
                c.setCommitteeContact(rs.getString("committee_contact"));
                committees.add(c);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return committees;
    }
    
    public Committee getCommittee(String id) {
        Committee c = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from committees where committee_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                c = new Committee();
                c.setCommitteeId(rs.getString("committee_id"));
                c.setCommitteeName(rs.getString("committee_name"));
                c.setCommitteeType(rs.getString("committee_type"));
                c.setCommitteeEmail(rs.getString("committee_email_id"));
                c.setCommitteePassword(rs.getString("committee_password"));
                c.setCommitteeRegistrationDate(rs.getString("committee_reg_datetime"));
                c.setIsVerified(rs.getInt("committee_isverified"));
                c.setIsActive(rs.getInt("committee_isactive"));
                c.setParentId(rs.getString("parent_id"));
                c.setCommitteeContact(rs.getString("committee_contact"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }
    
    public Status createCommittee(Committee c) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from committees where committee_email_id=?");
            ps.setString(1, c.getCommitteeEmail());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                st = new Status();
                st.setStatus(-4);
                con.close();
                return st;
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = sdf.format(dt);
        c.setCommitteeRegistrationDate(finalDate);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        c.setCommitteePassword(bCryptPasswordEncoder.encode(c.getCommitteePassword()));
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM committees");
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
            String finalId = c.getCommitteeType().toUpperCase() + randomNumber + randomCharacters;
            c.setCommitteeId(finalId);
            ps = con.prepareStatement("insert into committees (committee_id, committee_name, committee_type, committee_email_id, committee_password, committee_reg_datetime, committee_isverified, committee_isactive, parent_id, committee_contact) values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, c.getCommitteeId());
            ps.setString(2, c.getCommitteeName());
            ps.setString(3, c.getCommitteeType());
            ps.setString(4, c.getCommitteeEmail());
            ps.setString(5, c.getCommitteePassword());
            ps.setString(6, c.getCommitteeRegistrationDate());
            ps.setInt(7, c.getIsVerified());
            ps.setInt(8, c.getIsActive());
            ps.setString(9, c.getParentId());
            ps.setString(10, c.getCommitteeContact());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
//    public void updateCommittee(Committee a) {
//        try {
//            Connection con = DBConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement("update students set student_first_name=?, student_middle_name=?, student_last_name=?, student_email_id=?, student_password=?, student_uid=?, course_id=?, student_reg_datetime=?, student_isverified=?, student_isactive=?, institute_id=? where student_id=?");
//            ps.setString(1, s.getCommitteeName());
//            ps.setString(2, s.getCommitteeEmailId());
//            ps.setString(3, a.getCommitteePassword());
//            ps.setString(4, a.getCommitteeId());
//            ps.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    
    public Status deleteCommittee(String id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from committees where committee_id=?");
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
    
    public String committeeLogin(User u) {
        String status = "0"; //start
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from committees where committee_email_id=?");
            ps.setString(1, u.getUserEmail());
            ResultSet rs = ps.executeQuery();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(rs.next()){
                if(bCryptPasswordEncoder.matches(u.getUserPassword(), rs.getString("committee_password")))
                {
                    if(rs.getInt("committee_isverified") == 1)
                    {
                        status = rs.getString("committee_id"); //Logged in
                    }
                    else
                    {
                        status = "-2"; // User not verified
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
    
    public Status verifyCommittee(Committee c) {
        Status st = null;
        Committee committee = getCommittee(c.getCommitteeId());
        if(c.getParentId().equals(committee.getParentId())) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update committees set committee_isverified=? where committee_id=?");
                ps.setInt(1, 1);
                ps.setString(2, c.getCommitteeId());
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
