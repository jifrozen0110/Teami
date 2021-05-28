package or.korea.teami.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import or.korea.teami.entity.User;
import or.korea.teami.repo.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SignControllerTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() throws Exception {
		userRepository.save(User.builder()
			.email("test@test.com")
			.password(passwordEncoder.encode("test1111"))
			.name("test1")
			.roles(Collections.singletonList("ROLE_USER"))
			.build());
	}

	@Test
	void signin() throws Exception {
		MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
		params.add("email","test@test.com");
		params.add("password","test1111");
		mockMvc.perform(post("/v1/signin").params(params))
			.andDo(print())
			.andExpect(status().isOk());

	}

	@Test
	void signup() throws Exception {
		MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
		params.add("email","test2@test.com");
		params.add("password","test2222");
		params.add("name","test2");
		mockMvc.perform(post("/v1/signup").params(params))
			.andDo(print())
			.andExpect(status().isOk());
	}
}