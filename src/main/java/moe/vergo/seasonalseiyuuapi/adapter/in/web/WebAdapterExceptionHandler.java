package moe.vergo.seasonalseiyuuapi.adapter.in.web;

import moe.vergo.seasonalseiyuuapi.application.exception.GetSeiyuuDetailsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebAdapterExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(GetSeiyuuDetailsException.class)
    public ResponseEntity<String> handleGetSeiyuuDetailsException(GetSeiyuuDetailsException e) {
        LOGGER.error("GetSeiyuuDetailsException caught by exception handler, presenting to client as Service Unavailable", e);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Failed to fetch seiyuu details from upstream Rest API, try again later");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> catchAllExceptionsAndPresentAsInternalServerError(Exception e) {
        LOGGER.error("Exception caught by catch-all method, presenting to client as generic Internal Server error", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on our side - get in touch so we can look into it");
    }
}
