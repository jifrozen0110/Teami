package or.korea.teami.service;

import org.springframework.http.ResponseEntity;

import or.korea.teami.dto.UserDto;

public interface UserService {

	UserDto getUser(String email);

	void modifyProfile(String email, String name);

	void delete(String email);
}
