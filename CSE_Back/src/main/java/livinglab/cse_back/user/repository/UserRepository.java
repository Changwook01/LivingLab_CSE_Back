package livinglab.cse_back.user.repository;

import livinglab.cse_back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
