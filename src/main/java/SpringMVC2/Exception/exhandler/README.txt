[HTML 화면 오류 VS API 오류]
-HTML 화면 제공 시에는 BasicErrorController 사용이 간편
-API 오류는 각 시스템 마다 응답, 스펙이 다름
    ==> 세밀한 제어 필요
        ==> BasicErrorController 사용 / HandlerExceptionResolver 직접 구현으로는 번거로움
            ==> @ExceptionHandler 사용