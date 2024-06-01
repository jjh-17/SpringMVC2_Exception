package SpringMVC2.Exception.api;

import SpringMVC2.Exception.exception.BadRequestException;
import SpringMVC2.Exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/*
API 예외 컨트롤러 ==> @RestController

postman으로 테스트 해볼 것
 */
@Slf4j
@RestController
public class ApiExceptionController {

    // 테스트 용 DTO
    @Data
    @AllArgsConstructor
    public class MemberDto {
        private String id;
        private String name;
    }

    // exception 설정
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        switch (id) {
            case "ex" -> throw new RuntimeException("잘못된 사용자");
            case "bad" -> throw new IllegalArgumentException("잘못된 입력 값");
            case "user-ex" -> throw new UserException("사용자 오류");
        }
        
        return new MemberDto(id, "hello " + id);
    }

    //스프링 제공 ResponseStatusExceptionResolver1
    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    //스프링 제공 ResponseStatusExceptionResolver2
    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad",
                new IllegalArgumentException());
    }

    //스프링 제공 DefaultHandlerExceptionResolver
    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }
}
