package com.waaproject.waaprojectbackend.advice;

import com.waaproject.waaprojectbackend.dto.response.CustomResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class CustomResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // If the body is already a CustomResponse (e.g., in case of error responses), return it as is
        if (body instanceof CustomResponse) {
            return body;
        }

        // If the body is not a CustomResponse, wrap it in a CustomResponse with statusCode 200 and "SUCCESS" message
        CustomResponse<Object> customResponse = new CustomResponse<>();
        customResponse.setStatusCode(HttpStatus.OK.value());
        customResponse.setMessage("SUCCESS");
        customResponse.setData(body);

        // Catch ResponseStatusException and set the status code and message from it
        if (body instanceof ResponseStatusException ex) {
            customResponse.setStatusCode(ex.getStatusCode().value());
            customResponse.setMessage(ex.getReason());
        }

        return customResponse;
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomResponse> handleResponseStatusException(ResponseStatusException ex) {
        CustomResponse<Object> customResponse = new CustomResponse<>();
        customResponse.setStatusCode(ex.getStatusCode().value());
        customResponse.setMessage(ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(customResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CustomResponse> handleGenericError(Exception ex) {
        HttpStatus httpStatus = getResponseStatusAnnotationValue(ex.getClass());
        CustomResponse<Object> customResponse = new CustomResponse<>();
        customResponse.setStatusCode(httpStatus.value());
        customResponse.setMessage(ex.getMessage());
        return ResponseEntity.status(httpStatus).body(customResponse);
    }

    // Helper method to retrieve the @ResponseStatus annotation value from a class.
    private HttpStatus getResponseStatusAnnotationValue(Class<?> exceptionClass) {
        ResponseStatus annotation = exceptionClass.getAnnotation(ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        // Default to a status code of 500 (Internal Server Error) if the annotation is not found.
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}

