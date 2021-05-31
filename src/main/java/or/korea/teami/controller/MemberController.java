package or.korea.teami.controller;

import static or.korea.teami.entity.HttpStatusResponseEntity.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import or.korea.teami.advice.exception.UserNotFoundException;
import or.korea.teami.dto.UserDto;
import or.korea.teami.entity.User;
import or.korea.teami.repo.UserRepository;
import or.korea.teami.service.UserService;
import springfox.documentation.service.ResponseMessage;

@Api(tags = {"2 . member"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MemberController {

	private final UserService userService;

	private final UserRepository userRepository;

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value="회원 조회")
	@GetMapping(value = "/user")
	public ResponseEntity<User> findUser(){
		// SecurityContext에서 인증받은 회원의 정보를 얻어온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		return ResponseEntity.ok(userRepository.findByEmail(id).orElseThrow(UserNotFoundException::new));
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value="네임 수정")
	@PutMapping(value = "/user")
	public ResponseEntity<HttpStatus> modifyProfile(@ApiParam(value="회원 이름",required=true) @RequestParam String name){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		userService.modifyProfile(email,name);
		return RESPONSE_OK;
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value="회원 탈퇴")
	@DeleteMapping(value = "/user")
	public ResponseEntity<HttpStatus> delete(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		userService.delete(email);
		return RESPONSE_OK;
	}








}
