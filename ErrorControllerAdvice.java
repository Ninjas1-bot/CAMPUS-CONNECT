package com.example.campusconnect.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("stack", sw.toString());
        return "error-debug";
    }
}
