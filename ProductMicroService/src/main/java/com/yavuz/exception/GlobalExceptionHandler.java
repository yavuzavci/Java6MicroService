package com.yavuz.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Tüm istisnaların üzerinden geçtiği bir method oluşturuyorum ve Hata mesajını burada
     * dönüyorum.
     */
    private ErrorMessage createErrorMessage(EErrorType errorType, Exception exception){
        System.out.println("Hata oluştu....: " + exception.getMessage());
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

    /**
     * @ExceptionHandler -> Uygulama içinde oluşacak hatanın türünü bizden alarak
     * onun yakalanmasını sağlar, böylece yakaladığı istisnayı methoda geçer.
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception){
        return ResponseEntity.badRequest().body("Uygulamada beklenmeyen bir hata oluştu...: " + exception.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorMessage> handleSatisManagerException(UserServiceException exception){
        EErrorType errorType = exception.getErrorType();
        HttpStatus httpStatus = errorType.getHttpStatus();
        return new ResponseEntity<>(createErrorMessage(errorType,exception),httpStatus);
    }

    @ResponseBody
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(InvalidFormatException exception){
        EErrorType errorType = EErrorType.INVALID_PARAMETER;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        EErrorType errorType = EErrorType.METHOD_MISMATCH_ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handle(HttpMessageNotReadableException exception){
        EErrorType errorType = EErrorType.METHOD_MISMATCH_ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }

    /**
     * http://localhost:9090/urun/findbyid/234/sort/desc
     * http://localhost:9090/urun/findbyid/sort/desc
     */

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        EErrorType errorType = EErrorType.METHOD_NOT_VALID_ARGUMENT_ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }
}
