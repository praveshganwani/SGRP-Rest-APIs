/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.service;

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
import org.govt.model.Committee;
import org.govt.model.Status;
import org.govt.repository.CommitteeRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("committees")
public class CommitteeService {
    CommitteeRepository cr = new CommitteeRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Committee> getCommittees() {
        return cr.getCommittees();
    }
    
    @GET
    @Path("committee/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Committee getCommittee(@PathParam("id") String id) {
        return cr.getCommittee(id);
    }
    
    @POST
    @Path("committee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createCommittee(Committee c) {
        Status st = cr.createCommittee(c);
        return st;
    }
    
//    @PUT
//    @Path("committee")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Committee updateCommittee(Committee c) {
//        if(cr.getCommittee(c.getCommitteeId()) == null)
//            cr.createCommittee(c);
//        else
//            cr.updateCommittee(c);
//        return c;
//    }
    
    @DELETE
    @Path("committee/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Status deleteCommittee(@PathParam("id") String id) {
        Committee c = cr.getCommittee(id);
        Status st = null;
        if(c != null) {
            st = cr.deleteCommittee(id);
        }
        return st;
    }
    
    @POST
    @Path("verify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status verifyCommittee(Committee c) {
        Status st = cr.verifyCommittee(c);
        return st;
    }
}
