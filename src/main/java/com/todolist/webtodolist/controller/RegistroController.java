package com.todolist.webtodolist.controller;

import com.todolist.webtodolist.model.dto.RegistrarUsuarioDTO;
import com.todolist.webtodolist.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroController {

    private UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        if (!model.containsAttribute("registroU")) {
            model.addAttribute("registroU", new RegistrarUsuarioDTO());
        }
        return "formulario-registro";
    }

    @PostMapping("/guardarusuario")
    public String nuevoUsuario(@Valid @ModelAttribute("registroU") RegistrarUsuarioDTO registrarUsuarioDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        System.out.println("00000000");

        if (bindingResult.hasErrors()) {
            System.out.println("LOOO " + bindingResult.getAllErrors());
            return "formulario-registro";
        }

        usuarioService.registrarUsuario(registrarUsuarioDTO);

        System.out.println("1111111");

        redirectAttributes.addFlashAttribute(
                "success",
                "Usuario registrado correctamente"
        );

        return "redirect:/login";
    }

}
