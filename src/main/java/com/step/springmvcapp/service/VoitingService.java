package com.step.springmvcapp.service;

import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import com.step.springmvcapp.entity.Elector;
import java.util.List;

public interface VoitingService {
    List <Candidate> findCandidates();
    Candidate findCandidateById(Long id);    
    void saveCandidate (Candidate candidate);    
    
    List <Elector> findElectors();
    Elector findElectorById(Long id);
    void saveElector(Elector elector);
    void vote (Long electorId, Long candidateId);
        
    void saveCandidateDetails(CandidateDetails details);
    CandidateDetails findCandidateDetailsById(Long id); 

    String hashPassword (String password);

}
