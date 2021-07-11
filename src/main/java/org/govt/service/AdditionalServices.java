/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static org.govt.configs.TestClass.randomPasswordGenerator;
import org.govt.model.Activity;
import org.govt.model.Committee;
import org.govt.model.Email;
import org.govt.model.NameById;
import org.govt.model.OTP;
import org.govt.model.RegistrationEmail;
import org.govt.model.Student;
import org.govt.repository.CommitteeRepository;
import org.govt.repository.StudentRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("additional")
public class AdditionalServices {
    StudentRepository sr = new StudentRepository();
    CommitteeRepository cr = new CommitteeRepository();
    
    public static String randomOTPGenerator() {
        int len = 5;
        
        // ASCII range - alphanumeric (0-9, a-z, A-Z)
        final String chars = "0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of loop choose a character randomly from the given ASCII range
        // and append it to StringBuilder instance

        for (int i = 0; i < len; i++) {
                int randomIndex = random.nextInt(chars.length());
                sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public NameById getName(@PathParam("id") String id) {
        NameById nbi = null;
        if(sr.getStudent(id) != null) {
            Student s = sr.getStudent(id);
            nbi = new NameById();
            nbi.setName(s.getStudentFullName());
        }
        else if(cr.getCommittee(id) != null) {
            Committee c = cr.getCommittee(id);
            nbi = new NameById();
            nbi.setName(c.getCommitteeName());
        } 
        return nbi;
    }
    
    @POST
    @Path("email")
    @Produces(MediaType.APPLICATION_JSON) 
    public String SendEmail(final Email e) throws Exception {
        String st;
        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    e.sendEmail();
                } catch (Exception ex) {
                    Logger.getLogger(AdditionalServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        };
        t1.start();
        st = "Sending Mail";
        return st;
    }
    
    @POST
    @Path("registration-email")
    @Produces(MediaType.APPLICATION_JSON) 
    public String SendStaticEmail(final RegistrationEmail e) throws Exception {
        String st;
        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    e.sendEmail();
                } catch (Exception ex) {
                    Logger.getLogger(AdditionalServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        };
        t1.start();
        st = "Sending Mail";
        return st;
    }
    
    @POST
    @Path("otp")
    @Produces(MediaType.APPLICATION_JSON) 
    public OTP SendOTP(Student s) {
        String otp = randomOTPGenerator();
        OTP o = new OTP();
        try {
            // Construct data
            String apiKey = "apikey=" + "qx4i6vMBArQ-Jo5BHXGceMtGF3OB6Qa72N5QjJvr8P";
            String message = "&message=" + "Hello Student, Your OTP is: "+otp;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + s.getStudentMobileNo();
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
            }
            rd.close();
            o.setOtp(otp);
            System.out.println(stringBuffer.toString());    
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            System.out.println("Error "+e);
            o.setOtp("0");
        }
        return o;
    }
}
