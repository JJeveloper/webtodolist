package com.todolist.webtodolist.service.impl;

import com.todolist.webtodolist.model.entity.Usuario;
import com.todolist.webtodolist.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByIdentificacion(username).
                orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(usuario.getIdentificacion(), usuario.getContrasena(),
                true, true, true, true,
                List.of(new SimpleGrantedAuthority(usuario.getRol().getRoles())));
    }

}
