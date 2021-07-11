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
import java.util.Random;
import org.govt.configs.DBConfig;
import org.govt.model.Admin;
import org.govt.model.Status;
import org.govt.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Pravesh Ganwani
 */
public class AdminRepository {
    
    public List<Admin> getAdmins() {
        List<Admin> admins = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from admins");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Admin a = new Admin();
                a.setAdminId(rs.getString("admin_id"));
                a.setAdminName(rs.getString("admin_name"));
                a.setAdminEmailId(rs.getString("admin_email_id"));
                a.setAdminPassword(rs.getString("admin_password"));
                admins.add(a);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return admins;
    }
    
    public Admin getAdmin(String id) {
        Admin a = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from admins where admin_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                a = new Admin();
                a.setAdminId(rs.getString("admin_id"));
                a.setAdminName(rs.getString("admin_name"));
                a.setAdminEmailId(rs.getString("admin_email_id"));
                a.setAdminPassword(rs.getString("admin_password"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return a;
    }
    
    public Status createAdmin(Admin a) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from admins where admin_email_id=?");
            ps.setString(1, a.getAdminEmailId());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                st = new Status();
                st.setStatus(-4);
                con.close();
                return st;
            }
            ps = con.prepareStatement("SELECT COUNT(*) FROM admins");
            rs = ps.executeQuery();
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
            String finalId = "AD" + randomNumber + randomCharacters;
            a.setAdminId(finalId);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            a.setAdminPassword(bCryptPasswordEncoder.encode(a.getAdminPassword()));
            ps = con.prepareStatement("insert into admins(admin_id, admin_name, admin_email_id, admin_password) values(?,?,?,?)");
            ps.setString(1, a.getAdminId());
            ps.setString(2, a.getAdminName());
            ps.setString(3, a.getAdminEmailId());
            ps.setString(4, a.getAdminPassword());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status updateAdmin(Admin a) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("update admins set admin_name=?, admin_email_id=?, admin_password=? where admin_id=?");
            ps.setString(1, a.getAdminName());
            ps.setString(2, a.getAdminEmailId());
            ps.setString(3, a.getAdminPassword());
            ps.setString(4, a.getAdminId());
            ps.executeUpdate();
            st = new Status();
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status deleteAdmin(String id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from admins where admin_id=?");
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
    
    public String adminLogin(User u) {
        String status = "0"; //start
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from admins where admin_email_id=?");
            ps.setString(1, u.getUserEmail());
            ResultSet rs = ps.executeQuery();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(rs.next()){
                if(bCryptPasswordEncoder.matches(u.getUserPassword(), rs.getString("admin_password")))
                {
                    status = rs.getString("admin_id");
                }
                else {
                    status = "-1"; // Wrong password
                }
            }
            else {
                status = "-3"; // User Not registered
            }
        } catch (Exception e) {
        }
        return status;
    }
}
