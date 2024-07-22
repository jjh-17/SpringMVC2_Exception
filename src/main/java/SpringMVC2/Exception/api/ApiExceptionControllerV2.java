package SpringMVC2.Exception.api;


import SpringMVC2.Exception.exception.UserException;
import SpringMVC2.Exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionControllerV2 {

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String id;
        private String name;
    }


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        switch (id) {
            case "ex" -> throw new RuntimeException("잘못된 사용자");
            case "bad" -> throw new IllegalArgumentException("잘못된 입력 값");
            case "user-ex" -> throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    //최상위 오류 처리
    //Exception을 상속한 자식예외 처리 이후, 부모인 Exception 처리 수행
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("INTERNAL_SERVER_ERROR 예외 감지");
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
        log.error("BAD_REQUEST 예외 감지");
        return new ErrorResult("BAD", e.getMessage());
    }


    //사용자 예외처리 클래스 사용
    //@ExceptionHandler가 아닌, 메서드 파라미터에 예외 지정 가능
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("UserException 예외 감지");
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
