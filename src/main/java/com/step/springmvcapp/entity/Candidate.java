package com.step.springmvcapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

@Entity (name = "Candidate")
 @JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate extends BaseEntity implements Serializable  { 
    private Long voices;
    @NotEmpty
    @Size (min=3, max=50) 
    private String firstName;

    @NotEmpty
    @Size (min=3, max=50)
    private String lastName;
    
    @Digits (integer=3, fraction =0)
    @Min(18)
    @Max(100)
    private Integer age;
    
    //один кандидат to много избирателей
    @OneToMany(mappedBy="candidate",
            cascade = CascadeType.ALL, 
            orphanRemoval = true,fetch = FetchType.LAZY) //fetch=FetchType.LAZY,
    @JsonIgnore
    private Set <Elector> electors = new HashSet <>();
    
    @OneToOne(
        mappedBy = "candidate",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private CandidateDetails details;

    public Candidate() {}

    public Candidate(String firstName, String lastName, Integer age, Long voices) {
        this.voices = voices;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    

    @Column (name="voices")
    public Long getVoices() {
        return voices;
    }

    public void setVoices(Long voices) {
        this.voices = voices;
    }
    
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Set<Elector> getElectors() {
        return electors;
    }

    public void setElectors(Set<Elector> electors) {
        this.electors = electors;
    }

    public CandidateDetails getDetails() {
        return details;
    }

    public void setDetails(CandidateDetails details) {
        this.details = details;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.firstName);
        hash = 31 * hash + Objects.hashCode(this.lastName);
        hash = 31 * hash + Objects.hashCode(this.age);
        hash = 31 * hash + Objects.hashCode(this.details);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Candidate other = (Candidate) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        return Objects.equals(this.details, other.details);
    }
    
    @Override
    public String toString() {
         return new ToStringCreator(this)
            .append("id", this.getId())
            .append("firstName", this.getFirstName())
            .append("lastName", this.getLastName())
            .append("voices", this.voices)      
            .toString();
    } 
}
