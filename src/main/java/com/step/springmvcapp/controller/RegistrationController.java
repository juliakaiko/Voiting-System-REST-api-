package com.step.springmvcapp.controller;

import com.itstep.myfirstspringapp.informationInBrowser.AppError;
import com.step.springmvcapp.entity.Elector;
import com.step.springmvcapp.entity.User;
import com.step.springmvcapp.service.VoitingServiceImpl;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
@ComponentScan(basePackages = "com.step.springmvcapp")
public class RegistrationController {

    @Autowired
    private VoitingServiceImpl service;
    
    @RequestMapping(value="/registration", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity  createCustomer (@Valid @RequestBody Elector elector, Errors errors,
            HttpSession httpSession){
        if (errors.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<ObjectError> errs = errors.getAllErrors();
            for (ObjectError error: errs){
                builder.append(error.getObjectName() + " : " + error.getDefaultMessage());
            }           
            return ResponseEntity.badRequest().body(" Enter correct data! "+builder.toString());
        } 
        if (!service.findElectors().contains(elector)) { 
            String hashPass = this.service.hashPassword(elector.getPassword());
            elector.setPassword(hashPass);
            this.service.saveElector(elector);
            //Adding new user in the session 
            User user = new User ();
            user.setLogin(elector.getLogin());
            user.setPassword(hashPass);
            httpSession.setAttribute("user", user);
            return new ResponseEntity (elector,HttpStatus.OK);
        } else 
            return new ResponseEntity<>(new AppError(HttpStatus.METHOD_NOT_ALLOWED.value(), 
                "A voter with such data has been already registered in the system"),HttpStatus.METHOD_NOT_ALLOWED);
    }
    

}
