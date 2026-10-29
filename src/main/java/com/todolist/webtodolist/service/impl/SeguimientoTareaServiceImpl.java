package com.todolist.webtodolist.service.impl;

import com.todolist.webtodolist.exception.ValidarFechaPosterior;
import com.todolist.webtodolist.model.dto.SeguimientoTareaDTO;
import com.todolist.webtodolist.model.entity.Estado;
import com.todolist.webtodolist.model.entity.SeguimientoTarea;
import com.todolist.webtodolist.model.entity.Usuario;
import com.todolist.webtodolist.repository.SeguimientoTareaRepository;
import com.todolist.webtodolist.repository.UsuarioRepository;
import com.todolist.webtodolist.service.interfaces.SeguimientoTareaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class SeguimientoTareaServiceImpl implements SeguimientoTareaService {

    private SeguimientoTareaRepository seguimientoTareaRepository;
    private final UsuarioRepository usuarioRepository;

    public SeguimientoTareaServiceImpl(SeguimientoTareaRepository seguimientoTareaRepository, UsuarioRepository usuarioRepository) {
        this.seguimientoTareaRepository = seguimientoTareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<SeguimientoTareaDTO> listarSeguimientoTarea(Integer idTarea, Pageable pageable) {

        String identificacion = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByIdentificacion(identificacion).
                orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));

        return seguimientoTareaRepository.administrarSeguimientoTarea(idTarea, pageable);
    }

    @Override
    public Map<String, Integer> controlDiasTarea(Integer idSeguimiento, Estado estado) {

        SeguimientoTarea seguimiento = seguimientoTareaRepository
                .findById(idSeguimiento).orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));

        Map<String, Integer> tareaMap = new HashMap<>();

        LocalDate hoy = LocalDate.now();

        if (seguimiento.getFecha().isAfter(hoy)) {
            tareaMap.put("validacionFechaFutura", seguimiento.getTarea().getId());

            return tareaMap;
        }

        seguimiento.setEstado(estado);
        seguimientoTareaRepository.save(seguimiento);

        tareaMap.put("permitido", seguimiento.getTarea().getId());

        return tareaMap;

    }

}
