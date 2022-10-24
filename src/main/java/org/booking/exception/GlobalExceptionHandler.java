package org.booking.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView numberFormatExceptionHandler(HttpServletRequest request, ResponseStatusException exception) {
        return getModelAndView(request, exception.getStatus(), exception, exception.getReason());
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView numberFormatExceptionHandler(HttpServletRequest request, NumberFormatException exception) {
        return getModelAndView(request, HttpStatus.BAD_REQUEST, exception, exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView entityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException exception) {
        return getModelAndView(request, HttpStatus.NOT_FOUND, exception, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView accessDeniedExceptionHandler(HttpServletRequest request, AccessDeniedException exception) {
        return getModelAndView(request, HttpStatus.FORBIDDEN, exception, "Доступ заборонено");
    }

    private ModelAndView getModelAndView(HttpServletRequest request, HttpStatus httpStatus,
                                         Exception exception, String message) {
        log.error("Exception {} raised = {} :: URL = {}", exception.getClass(), exception.getMessage(), request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", httpStatus.value() + " / " + httpStatus.getReasonPhrase());
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}