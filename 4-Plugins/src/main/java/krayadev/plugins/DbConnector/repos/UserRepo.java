package krayadev.plugins.DbConnector.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import krayadev.domain.user.User;

public interface UserRepo extends JpaRepository<User, String> {}
