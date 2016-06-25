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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
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
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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

    //Since the error inspection see errors here, comment it. It can work anyway
//    @Autowired
//    private void injectOtherPersons(Person p1, Person p2, Map<String, Person> pc, ApplicationContext applicationContext){
//
//        log.info("1st person's name is:  " + p1.getName());
//        log.info("2nd person's name is:  " + p2.getName());
//        log.info("person collection is:  " + pc.size());
//        log.info("applicationContext  is:  " + applicationContext);
//    }

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
        return "logout1";
    }

    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping("/efg")
    public String efg() {
        return "efg";
    }
    @Transactional
    @RequestMapping(path="/user/{name}"
            //,headers = {"Content-type=application/json"}
    )
    public ResponseEntity<User> getUser(@PathVariable String name) throws Exception{
        //Another way to get the request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("Principal is:  " + request.getUserPrincipal().getName());

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
        log.info("User is:  " + user);
        CsrfToken token = generateCsrfToken(request);
        //Use this style, to add the real csrf token to the response header
        //Even spring mvc has a csrf protection, it will not add the csrf field in a custom response.
        //The token will be kept in a session, if the server is restarted, the token is expired.
        return ResponseEntity.ok().header("X-CSRF-TOKEN",token.getToken()).body(user);
        //return ResponseEntity.ok().body(user);

    }

    @RequestMapping(path = "/user", method = RequestMethod.POST
            ,headers = {"Content-type=application/json"}//这个头可以说是必须的，不然json格式也无法序列化为对象。猜也猜不出。
    )
    //678 在加载后重新开一个窗体是可以用的管理员账号，但用旧的csrf token 就会出问题？
    public ResponseEntity<User> addUser(@Valid//The valid annotation will not make new added user can not be read!
                                            @RequestBody User user, BindingResult result
            , HttpServletRequest request, HttpServletResponse response
    ) throws Exception{
        log.info("Auth type is:  " + request.getAuthType());
        log.info("Principal is:  " + request.getUserPrincipal().getName());
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(null);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        CsrfToken token = generateCsrfToken(request);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @RequestMapping(value="/csrf-token", method=RequestMethod.GET)
    public @ResponseBody String getCsrfToken(HttpServletRequest request) {
        CsrfToken token = generateCsrfToken(request);
        return token.getToken();
    }

    @RequestMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    private CsrfToken generateCsrfToken(HttpServletRequest request) {
        return (CsrfToken)request.getAttribute(CsrfToken.class.getName());
    }


    //Authorized endpoint

    @RequestMapping("/role_assist")
    public String role_assist() {
        return "role_assist";
    }


    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }


    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST
    )
    public String logout(HttpServletRequest request) throws Exception {
        return "logout2";
    }
}
