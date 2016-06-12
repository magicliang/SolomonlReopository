package com.example.controller;

import com.example.Entity.Person;
import com.example.Entity.User;
import com.example.SpringBootMvcApplication;
import com.example.repository.UserRepository;
import com.example.repository.impl.AnotherUserRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Created by magicliang on 2016/2/25.
 */
@RestController
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(SpringBootMvcApplication.class);


    private  Person person;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private AnotherUserRepostitory anotherUserRepostitory;
    public HelloController(){

    }

    @Bean//A bean factory method
    Person getPerson(){
        return this.person;
    }

    @Inject
    private void injectOtherPersons(Person p1, Person p2, Map<String, Person> pc, ApplicationContext applicationContext){

        log.info("1st person's name is:  " + p1.getName());
        log.info("2nd person's name is:  " + p2.getName());
        log.info("person collection is:  " + pc.size());
        log.info("applicationContext  is:  " + applicationContext);
    }

    @Autowired//This annotation can be used to most types, better than resource and inject
    public HelloController(Person person){
        this.person = person;
        log.info("The person's name is: " + person.getName());
    }
    @RequestMapping("/abc")
    public String abc() {
        return "abc";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }



    @RequestMapping("logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping("/efg")
    public String efg() {
        return "efg";
    }
    @Transactional
    @RequestMapping(path="/user/{name}"
            //,headers = {"Content-type=application/json"}
    )
    public ResponseEntity<User> getUser(@PathVariable String name) throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        List<User> users = userRepository.findByName(name);
        users = userRepository.findByName1(name);
        Future<List<User>> asyncUsers= anotherUserRepostitory.findByName(name);
        while (!asyncUsers.isDone()) {
            Thread.sleep(10); //10-millisecond pause between each check
        }
        users = asyncUsers.get();
        User user = null;
        if(!users.isEmpty()){
            user = users.get(0);
        }
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(path = "/user", method = RequestMethod.POST
            ,headers = {"Content-type=application/json"}
    )
    public ResponseEntity<User> addUser(//@Valid
                                            @RequestBody User user, BindingResult result
            , HttpServletRequest request, HttpServletResponse response
    ) throws Exception{
        log.info("Auth type is:  " + request.getAuthType());
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(null);
        }
        user.setPassword(user.getPassword());
        return ResponseEntity.ok(userRepository.save(user));
    }

}
