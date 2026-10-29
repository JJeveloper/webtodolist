package com.todolist.webtodolist.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class MVCGlobalExceptionHandler {

    @ExceptionHandler(ValidarRangoFechas.class)
    public String validarFechaInicioFechaFin(ValidarRangoFechas ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("validarFechas", ex.getMessage());

        return "redirect:/agregartarea";
    }

    @ExceptionHandler(ValidarFechaPosterior.class)
    public String fechaPosterior(ValidarFechaPosterior ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("fechaPosterior", ex.getMessage());

        return "redirect:/agregartarea";
    }

}
