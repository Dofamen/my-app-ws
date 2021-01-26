package ma.s.myappws.respositories;

import ma.s.myappws.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRespository extends JpaRepository<UserEntity, Long> {
}
