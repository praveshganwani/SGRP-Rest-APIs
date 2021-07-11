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
import org.govt.model.Category;
import org.govt.model.Status;
import org.govt.repository.CategoryRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("categories")
public class CategoryService {
    CategoryRepository cr = new CategoryRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Category> getCategories() {
        return cr.getCategories();
    }
    
    @GET
    @Path("category/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Category getCategory(@PathParam("id") int id) {
        return cr.getCategory(id);
    }
    
    @POST
    @Path("category")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createCategory(Category c) {
        Status st = cr.createCategory(c);
        return st;
    }
    
    @PUT
    @Path("category")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status updateCategory(Category c) {
        Status st = null;
        if(cr.getCategory(c.getCategoryId()) == null)
            st = cr.createCategory(c);
        else
            st = cr.updateCategory(c);
        return st;
    }
    
    @DELETE
    @Path("category/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Status deleteCategory(@PathParam("id") int id) {
        Category c = cr.getCategory(id);
        Status st = null;
        if(c != null) {
            st = cr.deleteCategory(id);
        }
        return st;
    }
}
