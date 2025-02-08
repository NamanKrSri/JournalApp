package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// these cntrollers handles the https requests

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserServices userServices;
    @PostMapping
    void createUser(@RequestBody UserEntry user){
        userServices.saveNewUser(user);
    }
}
