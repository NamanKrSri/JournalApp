//Now this is version of our app where we will store the entities in Database.
//Nood will try to o all the programmig logic and Db interaction into the same controller pack but the expert says to
// make separate packs and shits to make things look more decent and understandable
//Controller-->Services-->Repository
//Controller will rcw api request and tell services to do whatever logic u need and fullfill the request of
// .. REst Api and services know rest api also want to interact with db to services will take help from repository
//and repository on services demands will deal with mongodb database.

package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.Services.JournalEntryServices;
import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;



@RestController
@RequestMapping("/User")
public class UserController {
    //See everything is in diff package so how we will call services to do this and that logic accord to our REST API needs??
    //^^ for the shit above dependency injection will come to help
    @Autowired
    JournalEntryServices JES;

    @Autowired
    UserServices userServices;



    @PostMapping("{userName}")
    void saveJournalEntriesOfUser(@PathVariable String userName, @RequestBody JournalEntry journalEntry){
        JES.saveEntry(journalEntry,userName);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//after user login his details are saved in securityContextholder
        String userName=authentication.getName();
        UserEntry savedUser=userServices.findByUserName(userName);
        if(savedUser!=null){
            savedUser.setUserName(user.getUserName());
            savedUser.setPassword(user.getPassword());
            userServices.saveNewUser(savedUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            //new comment added to check
        }
    }
}
