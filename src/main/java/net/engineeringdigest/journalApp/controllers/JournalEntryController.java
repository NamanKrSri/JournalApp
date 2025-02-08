//package net.engineeringdigest.journalApp.controllers;
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//import java.util.HashMap;
//
////case 1
//@RestController
//@RequestMapping("/Journal")
//public class JournalEntryController {
//     HashMap<Long, JournalEntry> tempdatabase=new HashMap();
//
//     @GetMapping("/get")
//     private List<JournalEntry> getAll(){
//         return new ArrayList<>(tempdatabase.values());
//     }
//     @PostMapping("/post")//once you give destination address(/post) it will be only availaible when you write this in url
//    public boolean createEntry(@RequestBody JournalEntry JE){
//         tempdatabase.put(JE.getId(),JE);
//         return true;//an indicator
//     }
//     @GetMapping("get/{myid}")
//    public JournalEntry getid(@PathVariable Long myid){
//         return tempdatabase.get(myid);
//     }
//     @PutMapping("put/{oldid}")//means lets update any id with our new entry
//    public JournalEntry putid(@PathVariable Long oldid, @RequestBody JournalEntry JE){
//         return tempdatabase.put(oldid,JE);
//     }
//     @DeleteMapping("delete/{id}")
//    public boolean deleteid(@PathVariable Long id){
//         tempdatabase.remove(id);
//         return true;
//     }
//}
////case 2
////@RestController
////@RequestMapping("/Journal")//now this whole class will react to the http request
////public class JournalEntryController {
////     HashMap<Long, JournalEntry> tempdatabase=new HashMap();
////
////     @GetMapping//now as user will write server name and as he click on get method the following process will run
////     private List<JournalEntry> getAll(){
////         return new ArrayList<>(tempdatabase.values());
////     }
////     @PostMapping
////    public boolean createEntry(@RequestBody JournalEntry JE){
////         tempdatabase.put(JE.getId(),JE);
////         return true;//an indicator
////     }
////}