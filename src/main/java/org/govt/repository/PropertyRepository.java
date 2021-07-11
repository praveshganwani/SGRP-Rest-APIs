/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.govt.configs.DBConfig;
import org.govt.model.Properties;

/**
 *
 * @author Pravesh Ganwani
 */
public class PropertyRepository {
    public Properties getProperties() {
        Properties property = new Properties();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from properties");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                property.setAccessKeyId(rs.getString("access_key_id"));
                property.setSecretAccessKey(rs.getString("secret_access_key"));
                property.setSessionToken(rs.getString("session_token"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return property;
    }
}
