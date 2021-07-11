/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.govt.model.AuthenticatedAdmin;
import org.govt.model.AuthenticatedCommittee;
import org.govt.model.AuthenticatedStudent;
import org.govt.model.Status;
import org.govt.model.User;
import org.govt.repository.AdminRepository;
import org.govt.repository.CommitteeRepository;
import org.govt.repository.StudentRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("login")
public class UserService {
    
    StudentRepository sr = new StudentRepository();
    CommitteeRepository cr = new CommitteeRepository();
    AdminRepository ar = new AdminRepository();
    
    @POST
    @Path("student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public AuthenticatedStudent loginStudent(User u) {
        String status = sr.studentLogin(u);
        AuthenticatedStudent as = new AuthenticatedStudent();
        if(status.equals("-1")) {
            as.setStatus(-1);
            as.setStudentDetails(null);
        }
        else if(status.equals("-2")) {
            Status st = new Status();
            as.setStatus(-2);
            as.setStudentDetails(null);
        }
        else if(status.equals("-3")) {
            Status st = new Status();
            as.setStatus(-3);
            as.setStudentDetails(null);
        }
        else if(status.equals("0")) {
            as.setStatus(0);
            as.setStudentDetails(null);
        }
        else if(status.equals("-100")) {
            as.setStatus(-100);
            as.setStudentDetails(null);
        }
        else {
            as.setStatus(1);
            as.setStudentDetails(sr.getStudent(status));
        }
        return as;
    }
    
    @POST
    @Path("committee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public AuthenticatedCommittee loginCommittee(User u) {
        String status = cr.committeeLogin(u);
        AuthenticatedCommittee ac = new AuthenticatedCommittee();
        if(status.equals("-1")) {
            ac.setStatus(-1);
            ac.setCommitteeDetails(null);
        }
        else if(status.equals("-2")) {
            ac.setStatus(-2);
            ac.setCommitteeDetails(null);
        }
        else if(status.equals("-3")) {
            ac.setStatus(-3);
            ac.setCommitteeDetails(null);
        }
        else if(status.equals("0")) {
            ac.setStatus(0);
            ac.setCommitteeDetails(null);
        }
        else {
            Status st = new Status();
            ac.setStatus(1);
            ac.setCommitteeDetails(cr.getCommittee(status));
        }
        return ac;
    }
    
    @POST
    @Path("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public AuthenticatedAdmin loginAdmin(User u) {
        String status = ar.adminLogin(u);
        AuthenticatedAdmin aa = new AuthenticatedAdmin();
        if(status.equals("-1")) {
            aa.setStatus(-1);
            aa.setAdminDetails(null);
        }
        else if(status.equals("-3")) {
            aa.setStatus(-3);
            aa.setAdminDetails(null);
        }
        else if(status.equals("0")) {
            Status st = new Status();
            aa.setStatus(0);
            aa.setAdminDetails(null);
        }
        else {
            aa.setStatus(1);
            aa.setAdminDetails(ar.getAdmin(status));
        }
        return aa;
    }
}
