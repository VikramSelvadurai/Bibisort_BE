package com.example.bigbisort_be.common.exception;

import com.example.bigbisort_be.common.Internationalization.Translator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static com.example.bigbisort_be.core.contact.service.ContactServiceImpl.ERROR_LOG_TEMPLATE;

@Component
@Slf4j
public class ExceptionUtils {
    private final Translator translator;
    private final HttpHeaders headers;

    public ExceptionUtils(final Translator translator) {
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.translator = translator;
    }

    /**
     * this method is used to handle Exception
     */
    public ResponseEntity getException(
            HttpStatus httpStatus, WebRequest request, Exception exception, String message) {
        final ResponseEntity responseEntity =
                new ResponseEntity(
                        APIError.builder()
                                .status(httpStatus.value())
                                .timestamp(LocalDateTime.now().toString())
                                .error(httpStatus.getReasonPhrase())
                                .message(translator.toLocale(message, new String[] {exception.getMessage()}))
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .build(),
                        headers,
                        httpStatus);
        log.error(ERROR_LOG_TEMPLATE, exception);
        log.error(ERROR_LOG_TEMPLATE, responseEntity);
        return responseEntity;
    }

    /**
     * this method is used to handle Exception
     */
    public ResponseEntity getExceptionWithoutTanslation(
            HttpStatus httpStatus, WebRequest request, Exception exception, String message) {
        final ResponseEntity responseEntity =
                new ResponseEntity(
                        APIError.builder()
                                .status(httpStatus.value())
                                .timestamp(LocalDateTime.now().toString())
                                .error(httpStatus.getReasonPhrase())
                                .message(message)
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .build(),
                        headers,
                        httpStatus);
        log.error(ERROR_LOG_TEMPLATE, exception);
        log.error(ERROR_LOG_TEMPLATE, responseEntity);
        return responseEntity;
    }
}
