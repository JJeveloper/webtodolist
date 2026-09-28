package com.todolist.webtodolist.service.impl;

import com.todolist.webtodolist.model.dto.RegistrarUsuarioDTO;
import com.todolist.webtodolist.model.entity.Rol;
import com.todolist.webtodolist.model.entity.Usuario;
import com.todolist.webtodolist.repository.RolRepository;
import com.todolist.webtodolist.repository.UsuarioRepository;
import com.todolist.webtodolist.security.PasswordConfig;
import com.todolist.webtodolist.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private  UsuarioRepository usuarioRepository;
    private  PasswordConfig passwordConfig;
    private  RolRepository rolRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordConfig passwordConfig, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordConfig = passwordConfig;
        this.rolRepository = rolRepository;
    }

    @Override
    public void registrarUsuario(RegistrarUsuarioDTO registrarUsuarioDTO) {

            Usuario usuario = new Usuario();

            usuario.setIdentificacion(registrarUsuarioDTO.getIdentificacion());
            usuario.setNombres(registrarUsuarioDTO.getNombres());
            usuario.setContrasena(passwordConfig.passwordEncoder().encode(registrarUsuarioDTO.getContrasena()) );

            usuario.setFechaderegistro(LocalDateTime.now());

            Rol rol = rolRepository.findById(3).orElseThrow(
                    () -> new RuntimeException("Hubo un problema con el rol"));

            usuario.setRol(rol);

            usuario.setEnabled(true);

            usuarioRepository.save(usuario);




    }
}
