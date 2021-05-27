package or.korea.teami.advice;

import static or.korea.teami.entity.HttpStatusResponseEntity.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import or.korea.teami.advice.exception.PasswordNotMatchedException;
import or.korea.teami.advice.exception.SigninFailedException;
import or.korea.teami.advice.exception.UserNotFoundException;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(SigninFailedException.class)
	public ResponseEntity<HttpStatus> signinFailedException(){
		return RESPONSE_NOT_FOUND;
	}

	@ExceptionHandler(PasswordNotMatchedException.class)
	public ResponseEntity<HttpStatus> passwordNotMatchedException(){
		return RESPONSE_NOT_FOUND;
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<HttpStatus> userNotFoundException(){
		return RESPONSE_NOT_FOUND;
	}


}
