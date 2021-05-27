package or.korea.teami.repo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import or.korea.teami.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void findByEmail() {
		String email="test1@test.com";
		String name="test";
		//given
		userRepository.save(User.builder()
		.email(email)
		.password(passwordEncoder.encode("test1111"))
		.name(name)
		.roles(Collections.singletonList("ROLE_USER"))
		.build());
		//when
		Optional<User> user=userRepository.findByEmail(email);
		//then
		assertNotNull(user);
		assertTrue(user.isPresent());
		assertEquals(user.get().getName(),name);



	}
}