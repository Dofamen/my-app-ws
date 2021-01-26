package ma.s.myappws.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping(path = "/{id}")
    public String getUser(@PathVariable String id){
        return "getUser was called" + id;
    }





    @DeleteMapping
    public void deleteUser(){

    }


    @PutMapping
    public void updateUser(){

    }
}
