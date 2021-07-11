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
import org.govt.model.Keyword;
import org.govt.model.Status;
import org.govt.repository.KeywordRepository;

/**
 *
 * @author Pravesh Ganwani
 */
@Path("keywords")
public class KeywordService {
    KeywordRepository kr = new KeywordRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Keyword> getKeywords() {
        return kr.getKeywords();
    }
    
    @GET
    @Path("keyword/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Keyword getKeyword(@PathParam("id") String id) {
        return kr.getKeyword(id);
    }
    
    @POST
    @Path("keyword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createKeyword(Keyword k) {
        Status st = kr.createKeyword(k);
        return st;
    }
    
//    @PUT
//    @Path("keyword")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Status updateKeyword(Keyword k) {
//        Status st = null;
//        if(kr.getKeyword(k.getComplaintId()) == null)
//            st = kr.createKeyword(k);
//        else
//            st = kr.updateKeyword(k);
//        return st;
//    }
    
//    @DELETE
//    @Path("keyword/{id}")
//    public Status deleteKeyword(@PathParam("id") String id) {
//        Keyword k = kr.getKeyword(id);
//        Status st = null;
//        if(k != null) {
//            st = kr.deleteKeyword(id);
//        }
//        return st;
//    }
}
