package com.itstep.myfirstspringapp.repository;

import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateDetailsRepository extends JpaRepository <CandidateDetails, Long> {
    
    //@Query("from CandidateDetails c JOIN FETCH c.candidate WHERE c.id=:id")
     //@Query("from CandidateDetails c JOIN FETCH c.candidate WHERE c.id=?id")
   // CandidateDetails findAllCandidateDetailsForCandidate(@Param("id") Long id);

}
