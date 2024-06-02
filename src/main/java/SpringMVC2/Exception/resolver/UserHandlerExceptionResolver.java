package SpringMVC2.Exception.resolver;

import SpringMVC2.Exception.exception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

// UserException 처리를 위한 resolver
@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            //사용자 정의 ModelAndView 설정
            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String header = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if ("application/json".equals(header)) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ex", ex.getClass());
                    map.put("message", ex.getMessage());
                    
                    // HashMap을 String 문자열로 변환
                    String result = objectMapper.writeValueAsString(map);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);

                    return new ModelAndView();
                } else{
                    return new ModelAndView("error/500");
                }
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null;
    }
}
