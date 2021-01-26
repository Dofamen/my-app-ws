package ma.s.myappws.services.impl;

import ma.s.myappws.entities.UserEntity;
import ma.s.myappws.respositories.UserRespository;
import ma.s.myappws.services.UserService;
import ma.s.myappws.shared.dto.UserDto;
import ma.s.myappws.shared.dto.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayDeque;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRespository userRespository;
    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        //Optional<UserEntity> checkUser
        UserEntity checkUser = userRespository.findByEmail(userDto.getEmail());
        if (checkUser != null) throw  new RuntimeException("User Alrady Exists !");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUserID(utils.generateUSerID(32));

        UserEntity newUser = userRespository.save(userEntity);
        UserDto userDto1 = new UserDto();
        BeanUtils.copyProperties(newUser, userDto1);
        return userDto1;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRespository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRespository.findByEmail(email);

        if (userEntity == null) throw  new UsernameNotFoundException(email);

        return new User(userEntity.getFirstName(), userEntity.getEncryptedPassword(), new ArrayDeque<>());
    }
}
