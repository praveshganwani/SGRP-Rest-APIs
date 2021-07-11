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
import org.govt.model.Response;
import org.govt.model.Status;
import org.govt.repository.ResponseRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("responses")
public class ResponseService {
    ResponseRepository rr = new ResponseRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Response> getResponses() {
        return rr.getResponses();
    }
    
    @GET
    @Path("response/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Response> getResponse(@PathParam("id") String id) {
        return rr.getResponse(id);
    }
    
    @GET
    @Path("response/{id}/ASC")
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Response> getAscendingResponse(@PathParam("id") String id) {
        return rr.getAscendingResponse(id);
    }
    
    
    @POST
    @Path("response")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createResponse(Response r) {
        Status st = rr.createResponse(r);
        return st;
    }
    
//    @PUT
//    @Path("activity")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Status updateResponse(Response c) {
//        Status st = null;
//        if(cr.getResponse(c.getResponseId()) == null)
//            st = cr.createResponse(c);
//        else
//            st = cr.updateResponse(c);
//        return st;
//    }
    
//    @DELETE
//    @Path("activity/{id}")
//    public Status deleteResponse(@PathParam("id") int id) {
//        Response a = ar.getResponse(id);
//        Status st = null;
//        if(a != null) {
//            st = ar.deleteResponse(id);
//        }
//        return st;
//    }
}
