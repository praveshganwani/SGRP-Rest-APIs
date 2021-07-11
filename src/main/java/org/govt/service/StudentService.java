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
import org.govt.model.Status;
import org.govt.model.Student;
import org.govt.repository.StudentRepository;

/**
 *
 * @author Pravesh Ganwani
 */
@Path("students")
public class StudentService {
    StudentRepository sr = new StudentRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Student> getStudents() {
        return sr.getStudents();
    }
    
    @GET
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Student getStudent(@PathParam("id") String id) {
        return sr.getStudent(id);
    }
    
    @POST
    @Path("student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status createStudent(Student s) {
        Status st = sr.createStudent(s);
        return st;
    }
    
//    @PUT
//    @Path("student")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Student updateStudent(Student s) {
//        if(sr.getStudent(s.getStudentId()) == null)
//            sr.createStudent(s);
//        else
//            sr.updateStudent(s);
//        return s;
//    }
    
    @DELETE
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Status deleteStudent(@PathParam("id") String id) {
        Student s = sr.getStudent(id);
        Status st = null;
        if(s != null) {
            st = sr.deleteStudent(id);
        }
        return st;
    }
    
    @POST
    @Path("verify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status verifyStudent(Student s) {
        Status st = sr.verifyStudent(s);
        return st;
    }
    
    @POST
    @Path("block")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status blockStudent(Student s) {
        Status st = sr.blockStudent(s);
        return st;
    }
    
    @POST
    @Path("unblock")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    public Status unblockStudent(Student s) {
        Status st = sr.unblockStudent(s);
        return st;
    }
}
