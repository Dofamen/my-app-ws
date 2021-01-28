package ma.s.myappws.controllers;

import ma.s.myappws.exceptions.UserException;
import ma.s.myappws.requests.UserRequest;
import ma.s.myappws.response.ErrorMessages;
import ma.s.myappws.response.UserResponse;
import ma.s.myappws.services.UserService;
import ma.s.myappws.shared.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /*
    * for return XML format add on @...Mappin (produces = MediaType.APPLICATION_XML_VALUE)
    *
    *
    * for GET XML jSON INPUT OR RETURN THEM BOTH format add
    * ..Mapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
           produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    *
    */




    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
       UserDto userDto = userService.getUserByUssrId(id);
       UserResponse userResponse = new UserResponse();
       BeanUtils.copyProperties(userDto, userResponse);
       return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest)
    throws Exception{
        if (userRequest.getEmail().isEmpty() || userRequest.getFirstName().isEmpty() || userRequest.getLastName().isEmpty()
        || userRequest.getPassword().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest, userDto);
        UserDto createUser =  userService.createUser(userDto);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(createUser, userResponse);

        return  new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }



    @DeleteMapping(path = "/id")
    public ResponseEntity<Object> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest){

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest, userDto);
        UserDto userUpdate = userService.updateUser(id, userDto);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userUpdate, userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
    }
}
