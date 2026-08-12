package com.project.resuming.common.exception;


import com.project.resuming.common.response.ApiResTemplate;
import com.project.resuming.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@Component
@RequiredArgsConstructor
public class CustomExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResTemplate<Void>> handleServerException(Exception e){
        log.error("Internal Server Error: {}", e.getMessage(), e);

        ApiResTemplate<Void> response = ApiResTemplate.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }


    //커스텀 예외
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResTemplate<Void>> handleCustomException(BusinessException e){
        log.error("customException : {}" , e.getMessage(), e);

        ApiResTemplate<Void> apiResponse = ApiResTemplate.errorResponse(e.getErrorCode(), e.getMessage());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(apiResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResTemplate<Void>> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 에러 메시지 생성
        // 에러 메세지를 저장할 errorMap 생성
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // 응답 생성
        return new ResponseEntity<>(
                ApiResTemplate.errorResponse(
                        ErrorCode.VALIDATION_EXCEPTION,
                        ErrorCode.VALIDATION_EXCEPTION.getMessage() + convertMapToString(errorMap)
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    private String convertMapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 1); // 마지막 띄어쓰기 제거
        sb.deleteCharAt(sb.length() - 1); // 마지막 쉼표 제거
        return sb.toString();
    }


}
