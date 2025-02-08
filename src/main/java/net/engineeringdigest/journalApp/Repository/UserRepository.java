package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntry, ObjectId> {
    Optional<UserEntry> findById(ObjectId id);

    UserEntry findByUserName(String username);

    UserEntry save(UserEntry user);

}
