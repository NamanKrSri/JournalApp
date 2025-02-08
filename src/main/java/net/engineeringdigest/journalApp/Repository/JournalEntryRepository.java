//Here is the interface which will deal with mongodb to create fields and collections and do CRUD logics
package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry,ObjectId> {
//    List<JournalEntry> findAll();
    Optional<JournalEntry> findById(ObjectId id);

//    JournalEntry save(JournalEntry je, String userName);
}
