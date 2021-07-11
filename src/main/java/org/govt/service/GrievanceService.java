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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.govt.model.Category;
import org.govt.model.FAQs;
import org.govt.model.Grievance;
import org.govt.model.Status;
import org.govt.repository.FAQRepository;
import org.govt.repository.GrievanceRepository;

/**
 *
 * @author Pravesh Ganwani
 */
@Path("grievances")
public class GrievanceService {
    GrievanceRepository gr = new GrievanceRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Grievance> getGrievances() {
        return gr.getGrievances();
    }
    
    @GET
    @Path("grievance/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Grievance getGrievance(@PathParam("id") String id) {
        
        return gr.getGrievance(id);
    }
    
    @POST
    @Path("grievance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createGrievance(Grievance g) {
        Status st = gr.createGrievance(g);
        return st;
    }
    
//    @PUT
//    @Path("committee")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Grievance updateGrievance(Grievance c) {
//        if(cr.getGrievance(c.getGrievanceId()) == null)
//            cr.createGrievance(c);
//        else
//            cr.updateGrievance(c);
//        return c;
//    }
    
    @DELETE
    @Path("grievance/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Status deleteGrievance(@PathParam("id") String id) {
        Grievance g = gr.getGrievance(id);
        Status st = null;
        if(g != null) {
            st = gr.deleteGrievance(id);
        }
        return st;
    }
    
    @POST
    @Path("feedback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status giveFeedback(Grievance g) {
        Status st = gr.giveFeedback(g);
        return st;
    }
    
    @POST
    @Path("activity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status giveActivity(Grievance g) {
        Status st = gr.lastActivity(g);
        return st;
    }
    
    @POST
    @Path("dayselapsed")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status daysElapsed(Grievance g) {
        Status st = gr.daysElapsed(g);
        return st;
    }
    
    @POST
    @Path("solve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status solveGrievance(Grievance g) {
        Status st = gr.solveGrievance(g);
        return st;
    }
    
    @POST
    @Path("spam")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status isSpam(Grievance g) {
        Status st = gr.isSpam(g);
        return st;
    }
    
    @POST
    @Path("check/spam")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status checkForSpam(Grievance g) {
        Status st = gr.checkForSpam(g);
        return st;
    }
    
    @POST
    @Path("check/faq")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public FAQs checkForFaq(Grievance g) {
        FAQs f = gr.checkForFaq(g);
        return f;
    }
    
    @POST
    @Path("check/category")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Category checkForCategory(Grievance g) {
        Category c = gr.checkForCategory(g);
        return c;
    }
}
