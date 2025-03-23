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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Journal")
public class JournalEntryControllerV2 {
    //See everything is in diff package so how we will call services to do this and that logic accord to our REST API needs??
    //^^ for the shit above dependency injection will come to help
   @Autowired
    JournalEntryServices JES;
   @Autowired
    UserServices userServices;

   //*******OLD IMPEMENTATION WITHOUT SECURITY*********
//    @GetMapping("/get")
//    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
//        UserEntry user=userServices.findByUserName(userName);
//        List<JournalEntry> all=user.getJournalEntries();
//        if(all!=null && !all.isEmpty()){
//            return new ResponseEntity<>(all,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//********OLD IMPEMENTATION WITHOUT SECURITY**********

   @GetMapping("/get")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//after user login his details are saved in securityContextholder
        String userName=authentication.getName();
        UserEntry user=userServices.findByUserName(userName);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



//***old Method
//    @PostMapping("/post/{userName}")
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry JE,@PathVariable String userName){
//        try {
//            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//after user login his details are saved in securityContextholder
//            String userName=authentication.getName();
//            JES.saveEntry(JE,userName);
//            return new ResponseEntity<>(JE,HttpStatus.CREATED);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/post")
    public ResponseEntity<JournalEntry> createJournalEntryForUser(@RequestBody JournalEntry JE){
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//after user login his details are saved in securityContextholder
            String userName=authentication.getName();
                JES.saveEntry(JE,userName);
                return new ResponseEntity<>(JE,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    @GetMapping("get/{myid}")
//    public ResponseEntity<Optional<JournalEntry>> getid(@PathVariable ObjectId myid){
//        try {
//            Optional<JournalEntry> responseEntry=JES.findbyId(myid);
//            return new ResponseEntity<>(responseEntry,HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @GetMapping("get/{myid}")//Obtain specific JE of user with the help of ID of that specific JE.
    public ResponseEntity<Optional<JournalEntry>> getid(@PathVariable ObjectId myid){
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            UserEntry user=userServices.findByUserName(userName);
            List<JournalEntry> journalEntryList=user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
            if(!journalEntryList.isEmpty()){
                Optional<JournalEntry> responseEntry=JES.findbyId(myid);
                return new ResponseEntity<>(responseEntry,HttpStatus.OK);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//    @PutMapping("put/{oldid}")
//    public ResponseEntity<?> putid(@PathVariable ObjectId oldid, @RequestBody JournalEntry JE){
////        return JES.putId(oldid,JE);
//        try {
//            Object obj=JES.putId(oldid,JE);
//            if(obj==null){
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(obj,HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
    @PutMapping("put/{oldid}")
    public ResponseEntity<?> updateJournalEntryOfUser(@PathVariable ObjectId oldid, @RequestBody JournalEntry JE){
//        return JES.putId(oldid,JE);
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            UserEntry user=userServices.findByUserName(userName);
            List<JournalEntry> journalEntryList=user.getJournalEntries();
            boolean update=JES.updateJournalEntryOfUser(journalEntryList,JE,oldid);
            if(update){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteJournalEntryOfUserById( @PathVariable ObjectId id){
       boolean removed=false;
        //ResponseEntity<?> this ? is called wildcard pattern. Means now u can return your custom response if new
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            UserEntry user=userServices.findByUserName(userName);
            if(JES.findbyId(id).isPresent()){
                JES.deleteByID(id,userName,user);
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
