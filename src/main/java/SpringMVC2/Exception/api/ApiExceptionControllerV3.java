package SpringMVC2.Exception.api;


import SpringMVC2.Exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//ExControllerAdvice로 에러 코드를 분리
@Slf4j
@RestController
public class ApiExceptionControllerV3 {

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String id;
        private String name;
    }


    @GetMapping("/api3/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        switch (id) {
            case "ex" -> throw new RuntimeException("컨트롤러 Advice - 잘못된 사용자");
            case "bad" -> throw new IllegalArgumentException("컨트롤러 Advice - 잘못된 입력 값");
            case "user-ex" -> throw new UserException("컨트롤러 Advice - 사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }
}
