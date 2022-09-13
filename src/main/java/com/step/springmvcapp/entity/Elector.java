package com.step.springmvcapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.step.springmvcapp.entity.annotations.ValidPassportNumber;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.core.style.ToStringCreator;

@Entity (name = "Elector")
//@Table (name ="electors")
public class Elector extends User implements Serializable { 
    @NotNull (message = "Enter your First Name ")
    @Size (min=3, max=50, message = "First Name must be between 3 and 50 characters long ") 
    private String firstName;

    @NotNull (message = "Enter your Last Name ")
    @Size (min=3, max=50, message = "Last Name must be between 3 and 50 characters long ")
    private String lastName;
    
    @Digits (integer=3, fraction =0, message = "Enter only numbers in the age field! ")
    @Min(value=18, message = "Citizens under the age of 18 are not eligible to vote! ")
    @Max(value=120, message = "Enter correct age ")
    @NotNull(message = "Enter your age ")
    private Integer age;
    
    private boolean voted;
    
    @Size (min=2, max=2, message = "Your passport series must contain 2 letters ")
    @NotNull(message = "Enter your Passport Series " )
    private String passportSeries;
    
    @NotNull (message = "Enter your passport number ")
    @ValidPassportNumber (message = "Enter only numbers in the passport number field! ")
    @Size (min=7, max=7, message = "Your passport number must contain 7 digits ")
    private String passportNum;
    
    //много избирателей to одному избирателю
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Candidate candidate;
   
    public Elector() {}

    public Elector(String firstName, String lastName, Integer age, String passportSeries, String passportNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportSeries = passportSeries;
        this.passportNum = passportNum;
    }

    public Elector(String firstName, String lastName, Integer age, String passportSeries, String passportNum, String login, String password) {
        super(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportSeries = passportSeries;
        this.passportNum = passportNum;
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
    
    @Column(name = "PassportSeries")
    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    @Column(name = "PassportNumber", unique = true)
    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }
    
    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
    
    @Column(name = "Voted")
    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.firstName);
        hash = 53 * hash + Objects.hashCode(this.lastName);
        hash = 53 * hash + Objects.hashCode(this.age);
        hash = 53 * hash + Objects.hashCode(this.passportSeries);
        hash = 53 * hash + Objects.hashCode(this.passportNum);
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
        final Elector other = (Elector) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.passportSeries, other.passportSeries)) {
            return false;
        }
        if (!Objects.equals(this.passportNum, other.passportNum)) {
            return false;
        }
        return Objects.equals(this.age, other.age);
    }

    @Override
    public String toString() {
         return new ToStringCreator(this)
            .append("id", this.getId())
            .append("firstName", this.getFirstName())
            .append("lastName", this.getLastName())
            .append("age", this.getAge())
            .append("passportSeries", this.getPassportSeries())
            .append("passportNum", this.getPassportNum())
            .append("Login", this.getLogin())
            .append("Password", this.getPassword())
            .append("Voted", this.isVoted())    
            .toString();
    }  
    
    
    
    
   

}
