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
import org.govt.model.Activity;
import org.govt.model.Status;
import org.govt.repository.ActivityRepository;

/**
 *
 * @author Pravesh Ganwani
 */
@Path("activities")
public class ActivityService {
    ActivityRepository ar = new ActivityRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Activity> getActivities() {
        return ar.getActivities();
    }
    
    @GET
    @Path("activity/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Activity> getActivity(@PathParam("id") String id) {
        return ar.getActivity(id);
    }
    
    @GET
    @Path("activity/{id}/ASC")
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Activity> getAscendingActivity(@PathParam("id") String id) {
        return ar.getAscendingActivity(id);
    }
    
    @POST
    @Path("activity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createActivity(Activity a) {
        Status st = ar.createActivity(a);
        return st;
    }
    
//    @PUT
//    @Path("activity")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Status updateActivity(Activity c) {
//        Status st = null;
//        if(cr.getActivity(c.getActivityId()) == null)
//            st = cr.createActivity(c);
//        else
//            st = cr.updateActivity(c);
//        return st;
//    }
    
//    @DELETE
//    @Path("activity/{id}")
//    public Status deleteActivity(@PathParam("id") int id) {
//        Activity a = ar.getActivity(id);
//        Status st = null;
//        if(a != null) {
//            st = ar.deleteActivity(id);
//        }
//        return st;
//    }
}
