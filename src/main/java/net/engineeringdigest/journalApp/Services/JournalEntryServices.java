//The intension of this package is that here we will write all the programming logic for
// rest api needs, i.e if rest api saying find this id then here we will be writing
// the logic to search that shit
package net.engineeringdigest.journalApp.Services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component//u made this below class indetify as Bean by Spring
@Slf4j
public class JournalEntryServices {
    @Autowired
    JournalEntryRepository JERepository;
    @Autowired
    UserServices userServices;
    @Autowired
    JournalEntry journalEntry;
    @Transactional
    public void saveEntry(JournalEntry JE, String userName){
       UserEntry user=userServices.findByUserName(userName);
       try {
           if (user != null) {
               JE.setDate(LocalDateTime.now());
               JournalEntry saved = JERepository.save(JE);
               user.getJournalEntries().add(saved);
//               user.setUserName(null);
               userServices.saveEntry(user);
           }
       }
       catch (Exception e){
           System.out.println(e.getMessage());
           throw new RuntimeException();
       }
    }
    public void save(JournalEntry JE){
        try {
            JERepository.save(JE);
        }
        catch (Exception e){
        }
    }
    public List<JournalEntry> getAll(JournalEntry journalEntry){
        return JERepository.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId id) {

        return JERepository.findById(id);
    }

    public JournalEntry putId(ObjectId oldId, JournalEntry journalEntry) {
        JournalEntry journalEntryOfOldID=findbyId(oldId).orElse(null);
        if(journalEntryOfOldID!=null){
            journalEntryOfOldID.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().equals("") ? journalEntry.getContent() : journalEntryOfOldID.getContent() );
            journalEntryOfOldID.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : journalEntryOfOldID.getTitle());
            JERepository.save(journalEntryOfOldID);
        }
        return journalEntryOfOldID;
    }
    @Transactional//since if any process fails at middle who process will terminate
    public void deleteByID(ObjectId id, String userName,UserEntry user){
        try{
            boolean removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userServices.saveEntry(user);
                JERepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("AN error occured since u tried deleting other id",e);
        }
    }

    public boolean updateJournalEntryOfUser(List<JournalEntry> journalEntryList, JournalEntry je, ObjectId oldId) {
        for(JournalEntry journalEntry:journalEntryList){
            if(journalEntry.getId().equals(oldId)){
                if (je.getTitle() != null) {
                    journalEntry.setTitle(je.getTitle());
                }
                if (je.getContent() != null) {
                    journalEntry.setContent(je.getContent());
                }
                JERepository.save(journalEntry);
                return true;
            }
        }
        return false;
    }
}
