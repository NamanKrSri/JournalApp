//Purpose of this POJO
//Think How DB will know what kind of data is coming to get saved? And what are the elements DB is going to store?
//To tell DB that what are the field we are saving we make a POJO... Now DB get to know that the fields of this POJO is what he needs
//... to store.
//See it is the basic info of the journal app. every user will post their journal out here so every post should
// have an id, title and the content.
package net.engineeringdigest.journalApp.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

//now this pojo is needed to get mapped with Database
@Document(collection = "je")//this annotation will map the DB to this POJO also naming the collection is Imp
@Data
@Component
@NoArgsConstructor
public class JournalEntry {
    @Id//this will make string ID as the primary key for our DB.
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

}
