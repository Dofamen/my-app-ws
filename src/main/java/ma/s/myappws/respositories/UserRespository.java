package ma.s.myappws.respositories;

import ma.s.myappws.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRespository extends JpaRepository<UserEntity, Long> {
    //Optional<UserEntity> findByEmail(@Nullable String email);
    UserEntity findByEmail(String email);
    UserEntity findByUserID(String userId);
}
