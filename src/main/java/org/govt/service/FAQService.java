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
import org.govt.model.FAQs;
import org.govt.model.Status;
import org.govt.repository.FAQRepository;

/**
 *
 * @author Pravesh Ganwani
 */
@Path("faqs")
public class FAQService {
    FAQRepository fr = new FAQRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<FAQs> getFAQs() {
        return fr.getFAQs();
    }
    
    @GET
    @Path("faq/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public List<FAQs> getFAQ(@PathParam("id") String id) {
        return fr.getFAQ(id);
    }
    
    @POST
    @Path("faq")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createFAQ(FAQs f) {
        Status st = fr.createFAQ(f);
        return st;
    }
    
//    @PUT
//    @Path("course")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON) 
//    public Status updateFAQ(FAQ c) {
//        Status st = null;
//        if(cr.getFAQ(c.getFAQId()) == null)
//            st = cr.createFAQ(c);
//        else
//            st = cr.updateFAQ(c);
//        return st;
//    }
    
//    @DELETE
//    @Path("faq/{id}")
//    @Produces(MediaType.APPLICATION_JSON) 
//    public Status deleteFAQ(@PathParam("id") String id) {
//        FAQs f = fr.getFAQ(id);
//        Status st = null;
//        if(f != null) {
//            st = fr.deleteFAQ(id);
//        }
//        return st;
//    }
}
