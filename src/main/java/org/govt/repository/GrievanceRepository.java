/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.govt.repository;

import io.github.crew102.rapidrake.RakeAlgorithm;
import io.github.crew102.rapidrake.data.SmartWords;
import io.github.crew102.rapidrake.model.RakeParams;
import io.github.crew102.rapidrake.model.Result;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.govt.configs.DBConfig;
import org.govt.model.Category;
import org.govt.model.Committee;
import org.govt.model.FAQs;
import org.govt.model.Grievance;
import org.govt.model.Keyword;
import org.govt.model.Status;
import org.govt.model.Student;

/**
 *
 * @author Pravesh Ganwani
 */
public class GrievanceRepository {
    public List<Grievance> getGrievances() {
        List<Grievance> grievances = new ArrayList<>();
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from grievances");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Grievance g= new Grievance();
                g.setComplaintId(rs.getString("complaint_id"));
                g.setComplaintTitle(rs.getString("complaint_title"));
                g.setComplaintDetail(rs.getString("complaint_detail"));
                g.setComplaintStudentId(rs.getString("complaint_student_id"));
                g.setComplaintCommitteeId(rs.getString("complaint_committee_id"));
                g.setCategoryId(rs.getInt("category_id"));
                g.setComplaintDateTime(rs.getString("complaint_datetime"));
                g.setComplaintIsSolved(rs.getInt("complaint_issolved"));
                g.setComplaintIsAnonymous(rs.getInt("complaint_isanonymous"));
                g.setComplaintIsRedFlag(rs.getInt("complaint_isredflag"));
                g.setDaysElapsed(rs.getInt("days_elapsed"));
                g.setLastActivity(rs.getString("last_activity"));
                g.setFeedback(rs.getString("feedback"));
                g.setFeedbackComment(rs.getString("feedback_comment"));
                g.setImageUrl(rs.getString("image_url"));
                g.setComplaintIsDelayed(rs.getInt("complaint_isdelayed"));
                g.setComplaintIsSpam(rs.getInt("complaint_isspam"));
                grievances.add(g);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return grievances;
    }
    
    public Grievance getGrievance(String id) {
        Grievance g = null;
        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from grievances where complaint_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                g = new Grievance();
                g.setComplaintId(rs.getString("complaint_id"));
                g.setComplaintTitle(rs.getString("complaint_title"));
                g.setComplaintDetail(rs.getString("complaint_detail"));
                g.setComplaintStudentId(rs.getString("complaint_student_id"));
                g.setComplaintCommitteeId(rs.getString("complaint_committee_id"));
                g.setCategoryId(rs.getInt("category_id"));
                g.setComplaintDateTime(rs.getString("complaint_datetime"));
                g.setComplaintIsSolved(rs.getInt("complaint_issolved"));
                g.setComplaintIsAnonymous(rs.getInt("complaint_isanonymous"));
                g.setComplaintIsRedFlag(rs.getInt("complaint_isredflag"));
                g.setDaysElapsed(rs.getInt("days_elapsed"));
                g.setLastActivity(rs.getString("last_activity"));
                g.setFeedback(rs.getString("feedback"));
                g.setFeedbackComment(rs.getString("feedback_comment"));
                g.setImageUrl(rs.getString("image_url"));
                g.setComplaintIsDelayed(rs.getInt("complaint_isdelayed"));
                g.setComplaintIsSpam(rs.getInt("complaint_isspam"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return g;
    }
    
    public Status checkForSpam(Grievance g) {
        Status st = null;
        try {
            String details = g.getComplaintDetail();
            Pattern pattern = Pattern.compile("\\s\\s\\s");
            Matcher matcher = pattern.matcher(details);
            boolean found = matcher.find();
            if(found) {
                st = new Status();
                st.setStatus(-100);
                return st;
            }
            else {
                String[] words = details.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    // You may want to check for a non-word character before blindly
                    // performing a replacement
                    // It may also be necessary to adjust the character class
                    words[i] = words[i].replaceAll("[^\\w]", "");
                    if(words[i].length()>=15) {
                        st = new Status();
                        st.setStatus(-100);
                        return st;
                    }
                }
            }
        } 
        catch (Exception e) {
            st.setStatus(0);
            return st;
        }
        st = new Status();
        st.setStatus(1);
        return st;
    }
    
    public FAQs checkForFaq(Grievance g) {
        FAQs finalFaq = null;
        try {
            String[] stopWords = new SmartWords().getSmartWords(); 
            String[] stopPOS = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"}; 
            int minWordChar = 1;
            boolean shouldStem = true;
            String phraseDelims = "[-,.?():;\"!/]"; 
            RakeParams params = new RakeParams(stopWords, stopPOS, minWordChar, shouldStem, phraseDelims);
            
            ClassLoader classLoader = getClass().getClassLoader();
            File POSFile = new File(classLoader.getResource("/model-bin/en-pos-maxent.bin").getFile());
            File SentDetecFile = new File(classLoader.getResource("/model-bin/en-sent.bin").getFile());
            String POStaggerURL = POSFile.getAbsolutePath(); // The path to your POS tagging model
            String SentDetecURL = SentDetecFile.getAbsolutePath(); // The path to your sentence detection model
            RakeAlgorithm rakeAlg = new RakeAlgorithm(params, POStaggerURL, SentDetecURL);
            // Call the rake method
            String txt = g.getComplaintDetail();
            Result result = rakeAlg.rake(txt);
            // Print the result
            System.out.println(result.distinct());
            String keywords = result.toString().substring(1, result.toString().length() -1);
            String[] words = keywords.split(", ");
            FAQRepository fr = new FAQRepository();
            List<FAQs> listOfFaqs = fr.getFAQ(g.getComplaintCommitteeId());
            int[] count = {0,0,0,0,0,0,0,0,0};
            for(int i = 0; i < words.length; i++) {
                String word = words[i].replaceAll("\\s\\(.*?\\) ?", "");
                System.out.println(word+"\n");
                for(Iterator<FAQs> f = listOfFaqs.iterator(); f.hasNext();) {
                    FAQs faq = f.next();
                    if(faq.getFaqKeywords().toLowerCase().contains(word.toLowerCase())) {
                        finalFaq = faq;
                        return finalFaq;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return finalFaq;
    }
    
    public Category checkForCategory(Grievance g) {
        int maxIndex = 0;
        try {
            String[] stopWords = new SmartWords().getSmartWords(); 
            String[] stopPOS = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"}; 
            int minWordChar = 1;
            boolean shouldStem = true;
            String phraseDelims = "[-,.?():;\"!/]"; 
            RakeParams params = new RakeParams(stopWords, stopPOS, minWordChar, shouldStem, phraseDelims);
            
            ClassLoader classLoader = getClass().getClassLoader();
            File POSFile = new File(classLoader.getResource("/model-bin/en-pos-maxent.bin").getFile());
            File SentDetecFile = new File(classLoader.getResource("/model-bin/en-sent.bin").getFile());
            String POStaggerURL = POSFile.getAbsolutePath(); // The path to your POS tagging model
            String SentDetecURL = SentDetecFile.getAbsolutePath(); // The path to your sentence detection model
            RakeAlgorithm rakeAlg = new RakeAlgorithm(params, POStaggerURL, SentDetecURL);
            // Call the rake method
            String txt = g.getComplaintDetail();
            Result result = rakeAlg.rake(txt);
            // Print the result
            System.out.println(result.distinct());
            String keywords = result.toString().substring(1, result.toString().length() -1);
            String[] words = keywords.split(", ");
            String[] fileNames = {"examination", "infrastructure", "administration", "admission", "technical", "health-hygiene", "bullying-ragging", "library", "academics"};
            int[] count = {0,0,0,0,0,0,0,0,0};
            for(int i = 0; i < words.length; i++) {
                String word = words[i].replaceAll("\\s\\(.*?\\) ?", "");
                System.out.println(word+"\n");
                for(int j=0; j<fileNames.length; j++) {
                    File newFile = new File(classLoader.getResource("/res/"+fileNames[j]+".txt").getFile());
                    BufferedReader br = new BufferedReader(new FileReader(newFile));
                    String line;
                    while((line = br.readLine()) != null) {
                        String[] splitWords = word.split("\\s+");
                        for(int c=0; c<splitWords.length; c++) {
                            System.out.println(fileNames[j]+" line: "+line.toLowerCase()+" word:"+splitWords[c].toLowerCase());
                            if(line.toLowerCase().contains(splitWords[c].toLowerCase())) {
                                System.out.println("Matched");
                                System.out.println(line);
                                System.out.println(fileNames[j]);
                                count[j]++;
                            }
                        }
                    }
                }
            }
            int max = count[0];
            for(int k=1; k<count.length; k++) {
                if(count[k]>=max) {
                    max = count[k];
                    maxIndex = k;
                }
            }
            if(max == 0) {
                maxIndex = 9;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Category c = new Category();
        c.setCategoryId(maxIndex+1);
        return c;
    }
    
    public Status createGrievance(Grievance g) {
        Status st = null;
//        try {
//            Connection con = DBConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement("select * from grievance where committee_email_id=?");
//            ps.setString(1, c.getGrievanceEmail());
//            ResultSet rs = ps.executeQuery();
//            if(rs.next()) {
//                st = new Status();
//                st.setStatus(-4);
//                return st;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = sdf.format(dt);
        g.setComplaintDateTime(finalDate);

        try {
            Connection con = DBConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM grievances");
            ResultSet rs = ps.executeQuery();
            rs.next();
            String numbers = "123456789";
            Random generator = new Random();
            int randomIndex = generator.nextInt(numbers.length());
            int randomNumber = numbers.charAt(randomIndex);
            StringBuilder randomCharacters = new StringBuilder();
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            for(int i=0; i<4; i++)
            {
                Random characterGenerator = new Random();
                randomIndex = characterGenerator.nextInt(characters.length());
                randomCharacters.append(characters.charAt(randomIndex));
            }
            int totalRows = rs.getInt("COUNT(*)")+1;
            randomNumber = randomNumber*totalRows*23;
            String finalId = "GRIE" + randomNumber + randomCharacters;
            g.setComplaintId(finalId);
            
            StudentRepository studRep = new StudentRepository();
            CategoryRepository catRep = new CategoryRepository();
            CommitteeRepository comRep = new CommitteeRepository();
            KeywordRepository keyRep = new KeywordRepository();
            
            Student stud = studRep.getStudent(g.getComplaintStudentId());
            Committee com = comRep.getCommittee(stud.getInstituteId());
            Category cat = catRep.getCategory(g.getCategoryId());
            
            if(cat.getLowCommittee().equals("inst")) {
                g.setComplaintCommitteeId(com.getCommitteeId());
            }
            else {
                g.setComplaintCommitteeId(com.getParentId());
            }
            
            String[] stopWords = new SmartWords().getSmartWords(); 
            String[] stopPOS = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"}; 
            int minWordChar = 1;
            boolean shouldStem = true;
            String phraseDelims = "[-,.?():;\"!/]"; 
            RakeParams params = new RakeParams(stopWords, stopPOS, minWordChar, shouldStem, phraseDelims);
            
//            URL url = this.getClass().getClassLoader().getResource("AdminRepository.java");
//            URL url2 = this.getClass().getClassLoader().getResource("en-sent.bin");
//            System.out.println(url.toString());
            ClassLoader classLoader = getClass().getClassLoader();
            File POSFile = new File(classLoader.getResource("/model-bin/en-pos-maxent.bin").getFile());
            File SentDetecFile = new File(classLoader.getResource("/model-bin/en-sent.bin").getFile());
            File RedFlags = new File(classLoader.getResource("/res/stopwords-terrier.txt").getFile());
//            System.out.println(grandParent+"/model-bin/en-pos-maxent.bin");
            String POStaggerURL = POSFile.getAbsolutePath(); // The path to your POS tagging model
            String SentDetecURL = SentDetecFile.getAbsolutePath(); // The path to your sentence detection model
            RakeAlgorithm rakeAlg = new RakeAlgorithm(params, POStaggerURL, SentDetecURL);
            // Call the rake method
            String txt = g.getComplaintDetail();
            Result result = rakeAlg.rake(txt);
            // Print the result
            System.out.println(result.distinct());
            String keywords = result.toString().substring(1, result.toString().length() -1);
            String[] words = keywords.split(", ");
            

            BufferedReader br = new BufferedReader(new FileReader(RedFlags));
            String line;
            int flag = 0;
            while((line = br.readLine()) != null) {
                System.out.println(line);
                if(txt.toLowerCase().contains(line.toLowerCase())) {
                    g.setComplaintIsRedFlag(1);
                    flag = 1;
                    break;
                }
            }
            if(flag == 0) {
                g.setComplaintIsRedFlag(0);
            }
            
            ps = con.prepareStatement("insert into grievances (complaint_id, complaint_title, complaint_detail, complaint_student_id, complaint_committee_id, category_id, complaint_datetime, complaint_issolved, complaint_isanonymous, complaint_isredflag, image_url) values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, g.getComplaintId());
            ps.setString(2, g.getComplaintTitle());
            ps.setString(3, g.getComplaintDetail());
            ps.setString(4, g.getComplaintStudentId());
            ps.setString(5, g.getComplaintCommitteeId());
            ps.setInt(6, g.getCategoryId());
            ps.setString(7, g.getComplaintDateTime());
            ps.setInt(8, 0);
            ps.setInt(9, g.getComplaintIsAnonymous());
            ps.setInt(10, g.getComplaintIsRedFlag());
            ps.setString(11, g.getImageUrl());
            ps.executeUpdate();
            st = new Status();
            for(int i = 0; i < words.length; i++) {
                String word = words[i].replaceAll("\\(.*?\\) ?", "");
                Keyword k = new Keyword();
                k.setKeywordName(word);
                k.setComplaintId(finalId);
                keyRep.createKeyword(k);
            }
            st.setStatus(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
//    public void updateGrievance(Grievance a) {
//        try {
//            Connection con = DBConfig.getConnection();
//            PreparedStatement ps = con.prepareStatement("update students set student_first_name=?, student_middle_name=?, student_last_name=?, student_email_id=?, student_password=?, student_uid=?, course_id=?, student_reg_datetime=?, student_isverified=?, student_isactive=?, institute_id=? where student_id=?");
//            ps.setString(1, s.getGrievanceName());
//            ps.setString(2, s.getGrievanceEmailId());
//            ps.setString(3, a.getGrievancePassword());
//            ps.setString(4, a.getGrievanceId());
//            ps.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    
    public Status deleteGrievance(String id) {
        Status st = null;
        try {
            Connection con = DBConfig.getConnection();
            GrievanceRepository gr = new GrievanceRepository();
            Grievance g = gr.getGrievance(id);
            if(g.getLastActivity() == null || g.getLastActivity().equals("")) {
                KeywordRepository kr = new KeywordRepository();
                kr.deleteKeyword(id);
                PreparedStatement ps = con.prepareStatement("delete from grievances where complaint_id=?");
                ps.setString(1, id);
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return st;
    }
    
    public Status giveFeedback(Grievance g) {
        Status st = null;
        Grievance grievance = getGrievance(g.getComplaintId());
        if(grievance.getComplaintIsSolved() == 1) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update grievances set feedback=?, feedback_comment=? where complaint_id=?");
                ps.setString(1, g.getFeedback());
                ps.setString(2, g.getFeedbackComment());
                ps.setString(3, g.getComplaintId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(-5); // Complaint Not Solved
        }
        return st;
    }
    
    public Status lastActivity(Grievance g) {
        Status st = null;
        Grievance grievance = getGrievance(g.getComplaintId());
        if(grievance.getComplaintIsSolved() == 0) {
            try {
                if(g.getLastActivity().equals("forward") || g.getLastActivity().equals("escalate")) {
                    g.setDaysElapsed(0);
                    Connection con = DBConfig.getConnection();
                    PreparedStatement ps = con.prepareStatement("update grievances set last_activity=?, days_elapsed=? where complaint_id=?");
                    ps.setString(1, g.getLastActivity());
                    ps.setInt(2, 0);
                    ps.setString(3, g.getComplaintId());
                    ps.executeUpdate();
                    con.close();
                }
                else {
                    Connection con = DBConfig.getConnection();
                    PreparedStatement ps = con.prepareStatement("update grievances set last_activity=? where complaint_id=?");
                    ps.setString(1, g.getLastActivity());
                    ps.setString(2, g.getComplaintId());
                    ps.executeUpdate();
                    con.close();
                }
                st = new Status();
                st.setStatus(1);
               
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(-5); // Complaint Solved
        }
        return st;
    }
    
    public Status solveGrievance(Grievance g) {
        Status st = null;
        Grievance grievance = getGrievance(g.getComplaintId());
        if(grievance.getComplaintIsSolved() == 0) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update grievances set complaint_issolved=?, last_activity=? where complaint_id=?");
                ps.setInt(1, 1);
                ps.setString(2, "solve");
                ps.setString(3, g.getComplaintId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(-5); // Complaint Solved
        }
        return st;
    }
    
    public Status daysElapsed(Grievance g) {
        Status st = null;
        Grievance grievance = getGrievance(g.getComplaintId());
        if(grievance.getComplaintIsSolved() == 0) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update grievances set days_elapsed=? where complaint_id=?");
                ps.setInt(1, g.getDaysElapsed());
                ps.setString(2, g.getComplaintId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(-5); // Complaint Solved
        }
        return st;
    }
    
    public Status isDelayed(Grievance g) {
        Status st = null;
        Grievance grievance = getGrievance(g.getComplaintId());
        if(grievance.getComplaintIsSolved() == 0) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update grievances set complaint_isdelayed=? where complaint_id=?");
                ps.setInt(1, g.getComplaintIsDelayed());
                ps.setString(2, g.getComplaintId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(-5); // Complaint Solved
        }
        return st;
    }
    
    public Status isSpam(Grievance g) {
        Status st = null;
        Grievance grievance = getGrievance(g.getComplaintId());
        if(grievance.getComplaintIsSolved() == 0) {
            try {
                Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement("update grievances set complaint_isspam=? where complaint_id=?");
                ps.setInt(1, g.getComplaintIsSpam());
                ps.setString(2, g.getComplaintId());
                ps.executeUpdate();
                st = new Status();
                st.setStatus(1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            st = new Status();
            st.setStatus(-5); // Complaint Solved
        }
        return st;
    }
    
}
