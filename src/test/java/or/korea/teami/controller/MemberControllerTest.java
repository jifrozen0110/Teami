package or.korea.teami.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import or.korea.teami.entity.User;
import or.korea.teami.repo.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private String token;

	@BeforeEach
	void setUp() throws Exception {
		userRepository.save(User.builder()
			.email("jifrozen@naver.com")
			.password(passwordEncoder.encode("test1111"))
			.name("test1")
			.roles(Collections.singletonList("ROLE_USER"))
			.build());

		MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
		params.add("email","jifrozen@naver.com");
		params.add("password","test1111");
		MvcResult result=mockMvc.perform(post("/v1/signin").params(params))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		String resultString = result.getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		token=jsonParser.parseMap(resultString).get("data").toString();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	public void invalidToken() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
			.get("/v1/users")
			.header("X-AUTH-TOKEN", "XXXXXXXXXX"))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/exception/entrypoint"));
	}

	@Test
	void findUser() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
			.get("/v1/user")
			.header("X-AUTH-TOKEN", token))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void modifyProfile() throws Exception {
		Optional<User> user = userRepository.findByEmail("jifrozen@naver.com");
		MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
		params.add("name","test22");
		mockMvc.perform(put("/v1/user")
			.params(params).header("X-AUTH-TOKEN", token))
			.andDo(print())
			.andExpect(status().isOk());

		assertEquals(user.get().getName(),"test22");


	}

	@Test
	void delete() throws Exception {
		Optional<User> user = userRepository.findByEmail("jifrozen@naver.com");
		assertTrue(user.isPresent());
		mockMvc.perform(MockMvcRequestBuilders
			.delete("/v1/user")
			.header("X-AUTH-TOKEN", token))
			.andDo(print())
			.andExpect(status().isOk());
		assertTrue(userRepository.findByEmail("jifrozen@naver.com").isEmpty());
	}
}