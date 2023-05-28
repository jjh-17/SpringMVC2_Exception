[HTML 페이지 VS API 오류]
-BasicErrorController는 HTML 페이지 오류 처리 시에는 편리하나, API 오류에서는 약하다.
-각 API, Controller 마다 서로 다른 응답이 필요하기 때문
    ==> @ExceptionHandler 사용