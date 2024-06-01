package SpringMVC2.Exception.exhandler.advice;

/*
@ExceptionHandler로 간편한 예외 처리 가능
    ==> 정상 코드와 에러 코드가 하나의 컨트롤러 내부에 존재
        ==> @ControllerAdvice/@RestControllerAdvice로 정상/에러 코드 분리
 */

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
//@RestControllerAdvice
public class ExControllerAdvice {

    //최상위 오류 처리
    //Exception을 상속한 자식예외 처리 이후, 부모인 Exception 처리 수행
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    /*
    IllegalArgumentException에 대한 예외 처리

    1. 컨트롤러 호출 결과 IllegalArgumentException 예외가 밖으로 던져짐
    2. ExceptionResolver 작동 ==> ExceptionHandlerExceptionResolver 실행(가장 높은 우선순위)
    3. IllegalArgumentException 처리 가능 @ExceptionHandler 탐색
    4. illegalExHandler 메서드 수행 ==> @RestController 이므로 @ResponseBody 적용
                                    ==> HTTP 컨버터 사용, JSON 객체 반환
    5. @ResponseStatus로 HTTP 상태 코드 지정
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) //상태 코드 지정 가능
    @ExceptionHandler(IllegalArgumentException.class) //정상 흐름처럼 보이게 한다
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    //사용자 예외처리 클래스 사용
    //@ExceptionHandler가 아닌, 메서드 파라미터에 예외 지정 가능
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
