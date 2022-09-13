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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping ("/") 
@ComponentScan(basePackages = "com.step.springmvcapp")
public class UserController {
    @Autowired
    private VoitingServiceImpl service; 
    
    @GetMapping ("/")
    public ResponseEntity <List<Elector>> getElectors (){
        return ResponseEntity.ok(service.findElectors());
    }
    
    @RequestMapping(value="/login", method = {RequestMethod.POST})
    public ResponseEntity<?> logUser(@Valid @RequestBody User user, Errors errors,
            HttpSession httpSession){
        if (errors.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<ObjectError> errs = errors.getAllErrors();
            for (ObjectError error: errs){
                builder.append(error.getObjectName().toString() + " : " + error.getDefaultMessage().toString()); 
            }           
            return ResponseEntity.badRequest().body(user+ " Enter correct data! "+builder.toString());
        }       
        String userPass = user.getPassword();
        String userHashPass = service.hashPassword(userPass);
        
        List <Elector> list = service.findElectors();
        for (Elector electorProfile: list){ 
            if (electorProfile.getLogin().equals(user.getLogin())
                & electorProfile.getPassword().equals(userHashPass)){
                httpSession.setAttribute("user", user);
                return new ResponseEntity (electorProfile,HttpStatus.OK);
            }      
        }
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), 
                    "The user is not found"),HttpStatus.NOT_FOUND);
    }      
}
