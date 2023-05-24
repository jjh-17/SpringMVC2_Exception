package SpringMVC2.Exception.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;


/*
[예외 발생, 오류 페이지 요청 흐름]
1. WAS(예외 전달받음) <== 필터 <== 서블릿 <== 인터셉터 <== 컨트로러(예외 발생)
2. WAS(에러 페이지 요청) ==> 필터 ==> 서블릿 ==> 인터셉터 ==> 컨트롤러 ==> View

필터, 인터셉터가 2번 호출되어 비효율적
    ==> 클라이언트 요청, 내부 페이지 요청(서버) 구분을 위한 DispatcherType
 */

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
            log.info("로그 필터 요청 [{}][{}][{}]", uuid, requestURI, request.getDispatcherType());

            //다음 필터 혹은 서블릿 호출
            chain.doFilter(request, response);
        } catch (Exception e){
            throw e;
        } finally {
            log.info("로그 필터 응답 [{}][{}][{}]", uuid, requestURI, request.getDispatcherType());
        }

    }
}