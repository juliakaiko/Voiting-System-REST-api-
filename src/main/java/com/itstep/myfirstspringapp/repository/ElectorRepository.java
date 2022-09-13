package com.itstep.myfirstspringapp.repository;

import com.step.springmvcapp.entity.Elector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectorRepository extends JpaRepository <Elector, Long> {
    
}
