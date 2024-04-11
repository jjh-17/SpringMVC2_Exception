package SpringMVC2.Exception.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE));

        //ex의 경우 NestedServletException 스프링이 한번 감싸서 반환
        log.info("ERROR_MESSAGE: {}", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }

    @RequestMapping("/error-page/4xx")
    public String errorPage4xx(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 4xx");
        printErrorInfo(request);
        return "error-page/4xx";
    }

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    /*
    ApiExceptionController 사용시, 사용자가 임의로 오류 페이지 Customizer, Controller 등을 설정했다면
    오류 발생 시 JSON 객체 대신 오류 페이지 HTML 반환
        ==> produces를 이용하여 클라이언트 요청 HTTP Header의 Accept 값이 application/json일 경우 해당 메서드 호출
    */
//    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Object>> errorPage500Api(
//            HttpServletRequest request, HttpServletResponse response) {
//        log.info("API errorPage 500");
//
//        HashMap<String, Object> result = new HashMap<>();
//        Exception ex = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
//        result.put("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
//        result.put("message", ex.getMessage());
//
//        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
//    }
}
