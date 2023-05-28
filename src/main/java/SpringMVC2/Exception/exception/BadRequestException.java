package SpringMVC2.Exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
[ResponseStatusExceptionResolver]
예외에 따라 HTTP 상태 코드 지정
    ==> @ResponseStatus 어노테이션 OR ResponseStatusException 예외
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") //메시지 사용
public class BadRequestException extends RuntimeException {

}
