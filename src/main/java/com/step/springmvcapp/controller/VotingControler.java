package com.step.springmvcapp.controller;

import com.itstep.myfirstspringapp.informationInBrowser.AppError;
import com.itstep.myfirstspringapp.informationInBrowser.MyMessage;
import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import com.step.springmvcapp.entity.Elector;
import com.step.springmvcapp.entity.User;
import com.step.springmvcapp.service.VoitingServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@SessionAttributes("user")
@ComponentScan(basePackages = "com.step.springmvcapp")
public class VotingControler {
    @Autowired
    private VoitingServiceImpl service;
    
    @GetMapping ("/candidates")
    public ResponseEntity<List<Candidate>> showCandidates (){
        return new ResponseEntity <List<Candidate>>(this.service.findCandidates(), HttpStatus.OK);
    }
    
    @GetMapping ("/candidates/{id}")
    public ResponseEntity showCandidateDetails (@PathVariable ("id") Long id){
        Candidate candidate = service.findCandidateById(id); 
        CandidateDetails details = candidate.getDetails();
        if(details==null){
            details = new CandidateDetails ();
            details.setCandidate(candidate); //!!!!
            details.setId(id);
            service.saveCandidateDetails(details);
            candidate.setDetails(details);
            service.saveCandidate(candidate);
        }else
            details = service.findCandidateDetailsById(id);
        return new ResponseEntity (details,HttpStatus.OK);
    }
    
    
    @PostMapping(value = "/candidates/voting/{id}")
    public ResponseEntity votedForCandidate(@PathVariable("id") Long id,
        @ModelAttribute("user") User user) {
        if (service.findCandidateById(id)==null){
            return ResponseEntity.badRequest().body(user+ ", there aren't a candidate with such id = "+id);
        }
        String userPass = user.getPassword();
        String userHashPass = service.hashPassword(userPass);
        List<Elector> electors_list = service.findElectors();
        ResponseEntity response =null;
        for (Elector electorVote : electors_list) { 
            if (electorVote.getLogin().equals(user.getLogin())& 
                electorVote.getPassword().equals(userHashPass)) {
                if (electorVote.isVoted()==false){
                    service.vote(electorVote.getId(), id); //Long electorId, Long candidateId  
                    response = new ResponseEntity (this.service.findCandidateById(id), HttpStatus.OK);
                    return response;
                } else{
                    response = new ResponseEntity(new AppError(HttpStatus.METHOD_NOT_ALLOWED.value(), 
                    "You have already voted!"),HttpStatus.METHOD_NOT_ALLOWED); 
                }
            }else{
                response =new ResponseEntity(new AppError(HttpStatus.UNAUTHORIZED.value(), 
                    "The user is not authorized"),HttpStatus.UNAUTHORIZED);
            }
                
        }
        return response;
    }
    
    @GetMapping(value = "/candidates/results")
    public ResponseEntity showVotingResults(ModelMap model){
        List <Candidate> candidates = service.findCandidates();  
        Long max = 0L;
        Candidate firstWinner = null;
        Candidate secondWinner = null;       
        
        List <Long> voices = new ArrayList<>();
        for (Candidate candidate: candidates){
            if(candidate.getVoices()==null)
                candidate.setVoices(0L); 
            voices.add(candidate.getVoices());
        }
        
        int pos = -1;       
        for (int j = 0; j<candidates.size(); j++){
            if (pos != -1){
                break;
            }
            Long voice =  candidates.get(j).getVoices();
            for (int i=0; i<voices.size(); i++){
                if (!voice.equals(voices.get(i))){
                    pos=i;
                    break;
                }else
                   pos=-1; 
            }  
        }
        if (pos == -1) {
            return new ResponseEntity<>(new MyMessage ("The elections did not "
                    + "take place because of the  equal number of votes for all candidates",
                    candidates),
                HttpStatus.OK); 
        }

        for (Candidate candidate: candidates){
            if(candidate.getVoices()==null){
                candidate.setVoices(0L); 
            }
            if(candidate.getVoices()>max){
                max=candidate.getVoices();
                firstWinner = candidate;  
            } 
        }
        candidates.remove(firstWinner);
        for (Candidate candidate: candidates){
            if(candidate.getVoices().equals(max)){
                secondWinner = candidate;
            }
        }
        if (firstWinner!=null&secondWinner!=null){
            List <Candidate> winners = new ArrayList<>();
            winners.add(firstWinner); winners.add(secondWinner);
            
            return new ResponseEntity (new MyMessage("The Winners will take place in the second stage of voting!",winners), 
                    HttpStatus.OK);  
        }
        return new ResponseEntity (new MyMessage("The winner of the voting is",firstWinner), HttpStatus.OK);
    }
    
}
