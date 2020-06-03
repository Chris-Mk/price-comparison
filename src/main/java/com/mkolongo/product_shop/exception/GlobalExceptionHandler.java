package com.mkolongo.product_shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ProductNotFoundException.class,
            CategoryNotFoundException.class,
            UsernameNotFoundException.class,
            GroceryListNotFoundException.class
    })
    public ModelAndView handlerException(RuntimeException ex) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("message", ex.getMessage());
        mav.addObject("statusCode", HttpStatus.NOT_FOUND);
        return mav;
    }

}
