package com.todolist.webtodolist.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

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

    @ExceptionHandler(EntityNotFoundException.class)
    public String manejarTareaNoEncontrada(EntityNotFoundException ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(
                "errorDelete",
                "La tarea que intentas eliminar no existe."
        );

        return "redirect:/mostrarmistareas";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String manejarErrorIntegridad(DataIntegrityViolationException ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(
                "errorDelete",
                "No se pudo eliminar la tarea porque tiene registros relacionados."
        );

        return "redirect:/mostrarmistareas";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String manejarAccesoDenegado(AccessDeniedException ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(
                "errorDelete",
                ex.getMessage()
        );

        return "redirect:/mostrarmistareas";
    }

}
