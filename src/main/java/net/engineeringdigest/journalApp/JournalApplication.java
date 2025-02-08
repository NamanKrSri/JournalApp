package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    //for this transaction to occur we need two thing
    //PlatformTransactionManager(Its an interface)
    //MongoTransactionManager(it implements that above interface)

    @Bean
    PlatformTransactionManager randomName(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
    //MongoDatabaseFactory this helps to perform operations on MongoDb
}