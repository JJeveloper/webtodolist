package com.todolist.webtodolist.service.interfaces;

import com.todolist.webtodolist.model.dto.RegistrarUsuarioDTO;
import com.todolist.webtodolist.model.entity.Usuario;

public interface UsuarioService {

    void registrarUsuario(RegistrarUsuarioDTO registrarUsuarioDTO);

}
