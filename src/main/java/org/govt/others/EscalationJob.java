/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.others;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.govt.model.Activity;
import org.govt.model.Category;
import org.govt.model.Committee;
import org.govt.model.Email;
import org.govt.model.Grievance;
import org.govt.model.Status;
import org.govt.model.Student;
import org.govt.repository.ActivityRepository;
import org.govt.repository.CategoryRepository;
import org.govt.repository.CommitteeRepository;
import org.govt.repository.GrievanceRepository;
import org.govt.repository.StudentRepository;

/**
 *
 * @author Pravesh Ganwani
 */
public class EscalationJob implements Runnable{
    GrievanceRepository gr = new GrievanceRepository();
    ActivityRepository ar = new ActivityRepository();
    CommitteeRepository cr = new CommitteeRepository();
    CategoryRepository catRep = new CategoryRepository();
    StudentRepository sr = new StudentRepository();
    
    @Override
    public void run() {
        System.out.println("here");
        List<Grievance> grievances = gr.getGrievances();
        for(Iterator<Grievance> g = grievances.iterator(); g.hasNext();) {
            Grievance grievance = g.next();
            if(grievance.getComplaintIsSolved() == 0 && grievance.getComplaintIsSpam() == 0) {
                if(grievance.getDaysElapsed() <= 6) {
                    grievance.setDaysElapsed(grievance.getDaysElapsed() + 1);
                    Status st = gr.daysElapsed(grievance);
                    if(st.getStatus() == 1) {
                        continue;
                    }
                }
                else if(grievance.getDaysElapsed() == 7) {
                    int forwarded = 0;
                    String committeeId = grievance.getComplaintCommitteeId();
                    List<Activity> activities = ar.getActivity(grievance.getComplaintId());
                    for(Iterator<Activity> a = activities.iterator(); a.hasNext();) {
                        Activity activity = a.next();
                        if((activity.getActivityType().equals("forward") || activity.getActivityType().equals("escalate")) && activity.getComplaintId().equals(grievance.getComplaintId())) {
                            forwarded = 1;
                            committeeId = activity.getActivityTo();
                            break;
                        }
                    }
                    // Send Email to committeeId.
                    if(forwarded == 1) {
                        grievance.setDaysElapsed(grievance.getDaysElapsed() + 1);
                        grievance.setComplaintIsDelayed(1);
                        Status st = gr.daysElapsed(grievance);
                        st = gr.isDelayed(grievance);
                    }
                    else {
                        Committee com = cr.getCommittee(committeeId);
                        if(com.getCommitteeType().equals("inst")) {
                            int categoryId = grievance.getCategoryId();
                            Category category = catRep.getCategory(categoryId);
                            if(category.getHighCommittee().equals("univ")) {
                                Activity act = new Activity();
                                act.setActivityType("escalate");
                                act.setActivityFrom(com.getCommitteeId());
                                act.setActivityTo(com.getParentId());
                                act.setComplaintId(grievance.getComplaintId());
                                Status st = ar.createActivity(act);
                                grievance.setLastActivity("escalate");
                                st = gr.lastActivity(grievance);
                                System.out.println("Escalated: "+grievance.getComplaintId()+"to:"+act.getActivityTo());
                                Email e = new Email();
                                e.setActivityFrom(act.getActivityFrom());
                                e.setSubject("Complaint Escalated");
                                e.setTitle("Your Complaint Was Escalated to "+act.getActivityTo());
                                e.setG(grievance);
                                Student s = sr.getStudent(grievance.getComplaintStudentId());
                                e.setTo(s.getStudentEmail().toLowerCase());
                                try {
                                    e.sendEmail();
                                } catch (Exception ex) {
                                    Logger.getLogger(EscalationJob.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else if(category.getHighCommittee().equals("inst")) {
                                grievance.setDaysElapsed(grievance.getDaysElapsed() + 1);
                                grievance.setComplaintIsDelayed(1);
                                Status st = gr.daysElapsed(grievance);
                                st = gr.isDelayed(grievance);
                            }
                        }
                        else if(com.getCommitteeType().equals("univ")) {
                            grievance.setDaysElapsed(grievance.getDaysElapsed() + 1);
                            grievance.setComplaintIsDelayed(1);
                            Status st = gr.daysElapsed(grievance);
                            st = gr.isDelayed(grievance);
                        }
                    }
                }
            }
            
        }
    }
}
