package or.korea.teami.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import or.korea.teami.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByEmail(String email);
}
