/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.govt.model.Feedback;
import org.govt.model.Status;
import org.govt.repository.FeedbackRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("feedbacks")
public class FeedbackService {
    FeedbackRepository fr = new FeedbackRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Feedback> getFeedbacks() {
        return fr.getFeedbacks();
    }
    
    @GET
    @Path("feedback/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Feedback getFeedback(@PathParam("id") int id) {
        return fr.getFeedback(id);
    }
    
    @POST
    @Path("feedback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createFeedback(Feedback f) {
        Status st = fr.createFeedback(f);
        return st;
    }
}
