package ma.s.myappws.services;

import ma.s.myappws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
        UserDto createUser(UserDto userDto);
        UserDto getUser(String email);
        UserDto getUserByUssrId(String UserId);
        UserDto updateUser(String userId, UserDto userDto);
        void deleteUser(String useId);
}
