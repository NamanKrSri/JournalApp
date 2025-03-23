package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.Services.JournalEntryServices;
import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// these cntrollers handles the https requests

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserServices userServices;
    @Autowired
    JournalEntryServices JES;

    @PostMapping
    void createUser(@RequestBody UserEntry user){

        userServices.saveNewUser(user);
    }
    @PostMapping("/post")//this is public JE to be added not specific for user
    public ResponseEntity<JournalEntry> addEntry(@RequestBody JournalEntry JE){
        try {
            JES.save(JE);
            return new ResponseEntity<>(JE, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
