/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.others;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Pravesh Ganwani
 */
@WebListener
public class BackgroundJobManager implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new EscalationJob(), 0, 1, TimeUnit.DAYS);
//        scheduler.scheduleAtFixedRate(new SomeHourlyJob(), 0, 1, TimeUnit.HOURS);
//        scheduler.scheduleAtFixedRate(new SomeQuarterlyJob(), 0, 15, TimeUnit.MINUTES);
//        scheduler.scheduleAtFixedRate(new SomeFiveSecondelyJob(), 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}