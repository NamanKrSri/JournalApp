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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;



@RestController
@RequestMapping("/Journal")
public class JournalEntryControllerV2 {
    //See everything is in diff package so how we will call services to do this and that logic accord to our REST API needs??
    //^^ for the shit above dependency injection will come to help
   @Autowired
    JournalEntryServices JES;
   @Autowired
    UserServices userServices;

    @GetMapping("/get/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName){
        UserEntry user=userServices.findByUserName(userName);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
//        journalEntry.setDate(LocalDateTime.now());
//         JES.getAll(journalEntry);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/post")
    public ResponseEntity<JournalEntry> addEntry(@RequestBody JournalEntry JE){
        try {
            JES.save(JE);
            return new ResponseEntity<>(JE,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/post/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry JE, @PathVariable String userName){
        try {
                JES.saveEntry(JE,userName);
                return new ResponseEntity<>(JE,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("get/{myid}")
    public ResponseEntity<Optional<JournalEntry>> getid(@PathVariable ObjectId myid){
//        JES.findbyId(myid).orElse(null);
        try {
            Optional<JournalEntry> responseEntry=JES.findbyId(myid);
            return new ResponseEntity<>(responseEntry,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("put/{oldid}")
    public ResponseEntity<?> putid(@PathVariable ObjectId oldid, @RequestBody JournalEntry JE){
//        return JES.putId(oldid,JE);
        try {
            Object obj=JES.putId(oldid,JE);
            if(obj==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(obj,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("delete/{userName}/{id}")
    public ResponseEntity<?> deleteid(@PathVariable String userName , @PathVariable ObjectId id){
        //ResponseEntity<?> this ? is called wildcard pattern. Means now u can return your custom response if new
        try {
            if(JES.findbyId(id)!=null){
                JES.deleteByID(id,userName);
                return new ResponseEntity<>(HttpStatus.OK);
            }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //this ResponseEntity<?> allowed us to return something like this-ResponseEntity<?>(User,HttpStatus.OK);-> means earlier if id not found we are return blank or false
            //... now we can return user class or anyother thing so that everytime we have some webpage if the id found we will go to the new page else our client existing page will be there or u have seen something html page containing 404 image
        }
    }
}
