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
import org.govt.model.Course;
import org.govt.model.Status;
import org.govt.repository.CourseRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("courses")
public class CourseService {
    CourseRepository cr = new CourseRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Course> getCourses() {
        return cr.getCourses();
    }
    
    @GET
    @Path("course/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Course getCourse(@PathParam("id") int id) {
        return cr.getCourse(id);
    }
    
    @POST
    @Path("course")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createCourse(Course a) {
        Status st = cr.createCourse(a);
        return st;
    }
    
    @PUT
    @Path("course")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status updateCourse(Course c) {
        Status st = null;
        if(cr.getCourse(c.getCourseId()) == null)
            st = cr.createCourse(c);
        else
            st = cr.updateCourse(c);
        return st;
    }
    
    @DELETE
    @Path("course/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Status deleteCourse(@PathParam("id") int id) {
        Course c = cr.getCourse(id);
        Status st = null;
        if(c != null) {
            st = cr.deleteCourse(id);
        }
        return st;
    }
}
