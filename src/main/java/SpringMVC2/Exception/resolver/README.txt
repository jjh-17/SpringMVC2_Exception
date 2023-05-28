[HandlerExceptionResolver]
-스프링 MVC가 제공
-컨트롤러 밖으로 던져진 예외를 해결하고, 그 동작 방식을 변경할 수 있도록 함
-줄여서 ExceptionHandler

<ExceptionResolver 이전>
-postHandler 호출되지 않음
                       preHandle
                          ^^
                          || 1. preHandle
     6. 예외 전달           ||             2. handler                   3. 예외 발생
WAS <============ Dispatcher Servlet <===================> 핸들러 어뎁터 ============> 핸들러(컨트롤러)
                          ||             4. 예외 전달
                          ||
                          ||  5. afterCompletion(ex)
                          VV
                    afterCompletion

<ExceptionResolver 이후>
-ExceptionResolver로 예외 동작 방식 커스텀 가능
-ExceptionResolver로 예외가 해결되어도 postHandle은 호출되지 않는다.
-ExceptionResolver로 ModelAndView 반환 ==> 정상 흐름처럼 보이게 하기 위함
    ==> 빈 ModelAndView: 뷰 렌더링 X, 정상 흐름으로 서블릿 리턴
    ==> ModelAndView: 뷰 렌더링
    ==> null: 다음 ExceptionResolver 실행. 없다면 예외 처리가 되지 않고 예외를 서블릿 밖으로 던짐

                       preHandle
                          ^^
                          || 1. preHandle
     8. 정상 응답          ||             2. handler                   3. 예외 발생
WAS <============ Dispatcher Servlet <===================> 핸들러 어뎁터 ============> 핸들러(컨트롤러)
                          ||             4. 예외 전달
                          ||
                          ||  5. 예외 해결 시도
                          ||=====================> ExceptionResolver(return ModelAndView)
                          ||
                          ||  7. afterCompletion
                          ||=====================> afterCompletion
                          ||
                          ||
                          || 6. render(model) 호출
                          ||
                          VV
                         View ===HTML 응답===>

[스프링 제공 ExceptionResolver]
1. ExceptionHandlerExceptionResolver
    -@ExceptionHandler 처리
    -대부분의 API 예외 처리

2. ResponseStatusExceptionResolver
    -HTTP 상태 코드 지정
    -BadRequestException.class

3. DefaultHandlerExceptionResolver
    -스프링 내부 기본 예외 처리
    -대부분 클라이언트가 HTTP 요청 정보를 잘못 호출해서 발생하는 문제