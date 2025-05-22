package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserServices userServices;
    @GetMapping("/all-Users")
    ResponseEntity<List<UserEntry>> getAllUser(){
        List<UserEntry> users=userServices.getAllUsers();
        if(users!=null && !users.isEmpty()){
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("create-admin")
    ResponseEntity<?> createAdmin(@RequestBody UserEntry user){
        userServices.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
