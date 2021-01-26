package ma.s.myappws.controllers;

import ma.s.myappws.Requests.UserRequest;
import ma.s.myappws.Response.UserResponse;
import ma.s.myappws.services.UserService;
import ma.s.myappws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public String getUser(@PathVariable String id){
        return "getUser was called" + id;
    }


    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest, userDto);
        UserDto createUser =  userService.createUser(userDto);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(createUser, userResponse);

        return  userResponse;
    }



    @DeleteMapping
    public void deleteUser(){

    }


    @PutMapping
    public void updateUser(){

    }
}
