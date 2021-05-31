package or.korea.teami.controller;

import static or.korea.teami.entity.HttpStatusResponseEntity.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import or.korea.teami.advice.exception.SigninFailedException;
import or.korea.teami.advice.exception.PasswordNotMatchedException;
import or.korea.teami.config.security.JwtTokenProvider;
import or.korea.teami.entity.User;
import or.korea.teami.repo.UserRepository;

@Api(tags = {"1 . Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	@ApiOperation(value = "로그인",notes = "로그인 한다.")
	@PostMapping(value = "/signin")
	public ResponseEntity<String> signin(@ApiParam(value="회원 ID : 이메일",required = true) @RequestParam String email,
										@ApiParam(value="비밀번호",required = true) @RequestParam String password){
		User user=userRepository.findByEmail(email).orElseThrow(SigninFailedException::new);
		if (!passwordEncoder.matches(password,user.getPassword())){
			throw new PasswordNotMatchedException();
		}

		return ResponseEntity.ok(jwtTokenProvider.createToken(String.valueOf(user.getEmail()),user.getRoles()));
	}

	@ApiOperation(value="회원가입",notes = "회원가입 한다.")
	@PostMapping(value="/signup")
	public ResponseEntity<HttpStatus> signup(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String email,
											@ApiParam(value = "비밀번호", required = true) @RequestParam String password,
											@ApiParam(value = "이름", required = true) @RequestParam String name){

		userRepository.save(User.builder()
			.email(email)
			.password(passwordEncoder.encode(password))
			.name(name)
			.roles(Collections.singletonList("ROLE_USER"))
			.build());
		return RESPONSE_OK;
	}




}
