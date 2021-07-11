/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.govt.configs.DBConfig;
import org.govt.model.Admin;
import org.govt.model.Status;
import org.govt.repository.AdminRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("admins")
public class AdminService {
    
    AdminRepository ar = new AdminRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Admin> getAdmins() {
        return ar.getAdmins();
    }
    
    @GET
    @Path("admin/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Admin getAdmin(@PathParam("id") String id) {
        return ar.getAdmin(id);
    }
    
    @POST
    @Path("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createAdmin(Admin a) {
        Status st = ar.createAdmin(a);
        return st;
    }
    
    @PUT
    @Path("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status updateAdmin(Admin a) {
        Status st = null;
        if(ar.getAdmin(a.getAdminId()) == null)
            st = ar.createAdmin(a);
        else
            st = ar.updateAdmin(a);
        return st;
    }
    
    @DELETE
    @Path("admin/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Status deleteAdmin(@PathParam("id") String id) {
        Admin a = ar.getAdmin(id);
        Status st = null;
        if(a != null) {
            st = ar.deleteAdmin(id);
        }
        return st;
    }
}
