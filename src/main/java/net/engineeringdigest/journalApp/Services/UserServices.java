package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
     public void saveEntry(UserEntry user){
         userRepository.save(user);
     }

     public void saveNewUser(UserEntry user){
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         user.setRoles(Arrays.asList("RandomRole"));
         userRepository.save(user);
     }
    public Optional<UserEntry> findById(ObjectId Id){
         return userRepository.findById(Id);
    }
    public UserEntry putId(ObjectId oldId, UserEntry journalEntry) {
        return null;
    }
    public List<UserEntry> getAllUsers(){
         return userRepository.findAll();
    }

//    public void deleteByID(ObjectId id){
//        UserRepository.deleteById(id);
//    }

    public UserEntry findByUserName(String userName) {
         return  userRepository.findByUserName(userName);
    }
}
