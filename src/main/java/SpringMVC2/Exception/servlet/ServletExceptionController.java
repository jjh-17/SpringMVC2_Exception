package SpringMVC2.Exception.servlet;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class ServletExceptionController {

    @GetMapping()
    public String servlet_welcome(){
        log.info("[예외 처리 시작]");
        return "servlet_index";
    }

    @GetMapping("/error")
    public void servlet_error() {
        log.error("예외 발생");
        throw new RuntimeException("서블릿 예외 발생");
    }

    @GetMapping("/error-404")
    public void servlet_error404(HttpServletResponse response) throws IOException {
        log.error("예외 404 발생");

        //서블릿 컨테이너에 오류가 발생하였음을 전달
        response.sendError(404, "404 오류");
    }

    @GetMapping("/error-4xx")
    public void servlet_error400(HttpServletResponse response) throws IOException {
        log.error("예외 400 발생");
        response.sendError(400, "400 오류");
    }

    @GetMapping("/error-500")
    public void servlet_error500(HttpServletResponse response) throws IOException {
        log.error("서블릿 예외 500 발생");
        response.sendError(500, "500 오류");
    }
}
