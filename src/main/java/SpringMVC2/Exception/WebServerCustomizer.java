package SpringMVC2.Exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/*
[서블릿 커스텀 오류 페이지 등록]
WAS가 예외를 전달 받음 ==> 오류 페이지 정보 확인 ==> 아래에서 지정된 경로를 오류 페이지로써 요청
이때, 웹 브라우저(클라이언트)는 서버 내부에서 이러한 일이 발생함을 모름
 */
//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //각 오류 페이지 생성
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        //에러 페이지 등록
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
