package or.korea.teami.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpStatusResponseEntity {

	public static final ResponseEntity<HttpStatus> RESPONSE_OK = ResponseEntity.status(HttpStatus.OK).build();

	public static final ResponseEntity<HttpStatus> RESPONSE_NOT_FOUND=ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}
