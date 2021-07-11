/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.others;

/**
 *
 * @author Pravesh Ganwani
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="SendMailsServlet", urlPatterns={"/SendMail"},asyncSupported = true)
public class SendMailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter out=resp.getWriter();     
        final AsyncContext ctx=req.startAsync();
        
        Thread t1=new Thread()
        {
            @Override
            public void run() 
            {
                for (int i = 0; i < 10; i++) 
                {
                    try 
                    {
                        out.println(i);
                        Thread.currentThread().sleep(1000);
                    }
                    catch (Exception e) 
                    {
                        System.out.println(e);
                    }
                }
                ctx.complete();
            }
        };
        
        t1.start();
        out.print("Done");
    }
    
}
