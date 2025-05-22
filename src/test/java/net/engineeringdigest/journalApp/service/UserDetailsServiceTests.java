package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.entity.UserEntry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest//this annotation is necessary
//if you don't write this anno then no matter what dependency u will autowire nothing will work since components(bean) are created
//... during runtime if the application won't be running then no beans will be created hence all DI will be useless so in order..
//... to test fxns u need to write this anno. It will command the JVM to do all the necessary stuffs(bean creation) without running the application for the testing
public class UserDetailsServiceTests {
    @Autowired
    UserRepository userRepository;
    @Disabled
    @Test
    void userTest(){
        UserEntry user=userRepository.findByUserName("Naman");
        assertTrue(!user.getJournalEntries().isEmpty());
        assertNotNull(userRepository.findByUserName("Naman"));
    }
    @CsvSource({
            "1,1,2",
            "7,8,15",
            "10,2,14"
    })
    @ParameterizedTest
    void Test2(int a, int b, int expected){
        assertEquals(expected, a+b);
    }
}
