package SpringMVC2.Exception.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("로그 필터 시작");
    }

    @Override
    public void destroy() {
        log.info("로그 필터 종료");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //HTTP를 사용하므로 다운 캐스트
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        //HTTP 요청 구분을 위한 uuid 생성
        String uuid = UUID.randomUUID().toString();

        try{
            // 요청의 DispatcherType 포함
            log.info("로그 필터 요청 [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);

            //다음 필터 혹은 서블릿 호출
            chain.doFilter(request, response);
        } catch (Exception e){
            log.error("에러 메시지 출력 : " + e.getMessage());
            throw e;
        } finally {
            log.info("로그 필터 응답 [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);
        }

    }
}