package SpringMVC2.Exception.exhandler.advice;

/*
@ExceptionHandler로 간편한 예외 처리 가능
    ==> 정상 코드와 에러 코드가 하나의 컨트롤러 내부에 존재
        ==> @ControllerAdvice/@RestControllerAdvice로 정상/에러 코드 분리
 */

import SpringMVC2.Exception.api.ApiExceptionControllerV3;
import SpringMVC2.Exception.exception.UserException;
import SpringMVC2.Exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*
[@ControllerAdvice]
-지정한 컨트롤러에 @ExceptionHandler, @InitBinder 기능 부여
-대상 미지정 시 모든 컨트롤러에 적용
-@RestControllerAdvice = @ControllerAdvice + @ResponseBody

[대상 지정]
-@ControllerAdvice(annotations = RestController.class):
    ==> @RestController가 붙은 모든 컨트롤러에 적용
-@ControllerAdvice("패키지 경로")
    ==> 특정 패키지 내 모든 컨트롤러에 적용
-@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
    ==> 특정 컨트롤러 지정
 */
@Slf4j
@RestControllerAdvice(assignableTypes = ApiExceptionControllerV3.class)
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //상태 코드 지정 가능
    @ExceptionHandler(IllegalArgumentException.class) //정상 흐름처럼 보이게 한다
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
