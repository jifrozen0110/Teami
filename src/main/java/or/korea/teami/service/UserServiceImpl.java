package or.korea.teami.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import lombok.RequiredArgsConstructor;
import or.korea.teami.advice.exception.UserNotFoundException;
import or.korea.teami.dto.UserDto;
import or.korea.teami.entity.User;
import or.korea.teami.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;


	@Override
	public UserDto getUser(String email) {
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(user,UserDto.class);

	}

	@Override
	public void modifyProfile(String email, String name) {
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		user.updateName(name);
	}

	@Override
	public void delete(String email) {
		User user=userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		userRepository.deleteById(user.getId());
	}
}
