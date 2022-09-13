package com.itstep.myfirstspringapp.repository;

import com.step.springmvcapp.entity.Candidate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository <Candidate, Long> {
    
   // @Query(value = "SELECT c FROM Candidate c JOIN FETCH c.electors")  
//    @Query(value = "SELECT c.id,c.firstName,c.lastName,c.age,c.voices FROM Candidate c")
//    List<Candidate> findAllCandidates();    
}
