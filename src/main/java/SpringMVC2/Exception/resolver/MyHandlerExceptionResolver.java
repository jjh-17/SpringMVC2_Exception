package SpringMVC2.Exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("call resolver", ex);

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");

                // HTTP 상태 코드 400 지정
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

                // 빈 ModelAndView 반환 ==> WAS까지 정상적으로 return 하도록 하기 위함
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        // 다음 ExceptionResolver를 찾아 실행
        // 처리 가능한 ExceptionResolver가 없으면 예외 처리 불가 => 기존 발생 예외를 서블릿 밖으로 throw
        return null;
    }
}
