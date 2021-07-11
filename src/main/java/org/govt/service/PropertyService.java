/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.govt.model.Activity;
import org.govt.model.Properties;
import org.govt.repository.PropertyRepository;

/**
 *
 * @author Pravesh Ganwani
 */

@Path("properties")
public class PropertyService {
    PropertyRepository pr = new PropertyRepository();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getProperties() {
        return pr.getProperties();
    }
}
