package nbc.schedule.User.infrastructure.persistence;

import nbc.schedule.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    java.util.Optional<User> findByEmail(String email);
}
