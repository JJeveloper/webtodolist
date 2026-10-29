package com.todolist.webtodolist.controller;

import com.todolist.webtodolist.model.dto.AdministrarTareaDTO;
import com.todolist.webtodolist.model.dto.ListarMisTareasDTO;
import com.todolist.webtodolist.model.dto.RegistrarTareaDTO;
import com.todolist.webtodolist.model.dto.SeguimientoTareaDTO;
import com.todolist.webtodolist.model.entity.Estado;
import com.todolist.webtodolist.service.interfaces.SeguimientoTareaService;
import com.todolist.webtodolist.service.interfaces.TareaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TareasController {

    private final TareaService tareaService;
    private final SeguimientoTareaService seguimientoTareaService;


    public TareasController(TareaService tareaService, SeguimientoTareaService seguimientoTareaService) {
        this.tareaService = tareaService;
        this.seguimientoTareaService = seguimientoTareaService;
    }

    @GetMapping("/agregartarea")
    public String formularioAgregartarea(Model model) {
        if (!model.containsAttribute("aTarea")) {
            model.addAttribute("aTarea", new RegistrarTareaDTO());
        }
        return "formulario-agregartarea";
    }

    @PostMapping("/guardartarea")
    public String agregartarea(@Valid @ModelAttribute("aTarea") RegistrarTareaDTO dto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((error) -> {
                System.out.println(error.getDefaultMessage());
            });
            return "formulario-agregartarea";
        }

        tareaService.guardarTarea(dto);

        redirectAttributes.addFlashAttribute(
                "successT",
                "Tarea agregada"
        );

        return "redirect:/agregartarea";
    }

    @GetMapping("/mostrarmistareas")
    public String mostrarMisTareas(@RequestParam(defaultValue = "0") int page, Model model) {

        Pageable pageable = PageRequest.of(page, 20);

        Page<ListarMisTareasDTO> lista = tareaService.todasMisTareas(pageable);

        model.addAttribute("tarea", lista);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", lista.getTotalPages());

        return "mis-tareas";
    }

    @GetMapping("/detalle-tarea/{id}")
    public String detalletarea(@PathVariable("id") Integer id,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {

        Pageable pageable = PageRequest.of(page, 5);

        AdministrarTareaDTO administrarTarea = tareaService.administrarTarea(id);

        Page<SeguimientoTareaDTO> listaDeFechas = seguimientoTareaService.listarSeguimientoTarea(id, pageable);

        model.addAttribute("listaDeFechas", listaDeFechas);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listaDeFechas.getTotalPages());

        model.addAttribute("mitarea", administrarTarea);

        return "administrar-tarea";
    }

    @PostMapping("/seguimiento/{id}/estado")
    public String actualizarEstado(@PathVariable Integer id, @RequestParam Estado estado,
            @RequestParam(defaultValue = "0") int page, RedirectAttributes redirectAttributes) {

        Map<String, Integer> idTarea = seguimientoTareaService.controlDiasTarea(id, estado);

        if (idTarea.containsKey("validacionFechaFutura")) {

            redirectAttributes.addFlashAttribute(
                    "errorSeguimiento",
                    "No puedes cambiar el estado de una fecha futura."
            );

            return "redirect:/detalle-tarea/"
                    + idTarea.get("validacionFechaFutura")
                    + "?page=" + page;
        }

        return "redirect:/detalle-tarea/"
                + idTarea.get("permitido")
                + "?page=" + page;

    }


    @PostMapping("/eliminar-tarea/{id}")
    public String eliminarTarea(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {


        try {
            tareaService.eliminarTarea(id);

            redirectAttributes.addFlashAttribute(
                    "successDelete",
                    "Tarea eliminada correctamente."
            );

        } catch (AccessDeniedException e) {

            redirectAttributes.addFlashAttribute(
                    "errorDelete",
                    "No tienes permiso para eliminar esta tarea."
            );

        } catch (EntityNotFoundException e) {

            redirectAttributes.addFlashAttribute(
                    "errorDelete",
                    "La tarea que intentas eliminar no existe."
            );

        } catch (DataIntegrityViolationException e) {

            redirectAttributes.addFlashAttribute(
                    "errorDelete",
                    "No se puede eliminar la tarea porque tiene registros relacionados."
            );
        }

        return "redirect:/mostrarmistareas";
    }

}
