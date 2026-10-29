package com.todolist.webtodolist.service.impl;

import com.todolist.webtodolist.exception.ValidarRangoFechas;
import com.todolist.webtodolist.model.dto.AdministrarTareaDTO;
import com.todolist.webtodolist.model.dto.ListarMisTareasDTO;
import com.todolist.webtodolist.model.dto.RegistrarTareaDTO;
import com.todolist.webtodolist.model.entity.*;
import com.todolist.webtodolist.repository.SeguimientoTareaRepository;
import com.todolist.webtodolist.repository.TareaRepository;
import com.todolist.webtodolist.repository.UsuarioRepository;
import com.todolist.webtodolist.service.interfaces.TareaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SeguimientoTareaRepository seguimientoTareaRepository;

    public TareaServiceImpl(TareaRepository tareaRepository, UsuarioRepository usuarioRepository, SeguimientoTareaRepository seguimientoTareaRepository) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
        this.seguimientoTareaRepository = seguimientoTareaRepository;
    }

    @Override
    public void guardarTarea(RegistrarTareaDTO registrarTareaDTO) {

        if (registrarTareaDTO.getFechaInicio().isAfter(registrarTareaDTO.getFechaFin())) {
            throw new ValidarRangoFechas("Fecha Inicio no puede ser mayor que Fecha Fin");
        }

        Tarea tarea = new Tarea();

        tarea.setTarea(registrarTareaDTO.getTarea());
        tarea.setDescripcion(registrarTareaDTO.getDescripcion());
        tarea.setFechainicio(registrarTareaDTO.getFechaInicio());
        tarea.setFechafin(registrarTareaDTO.getFechaFin());

        tarea.setVisibilidad(registrarTareaDTO.getVisibilidad());

        String identificacion = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByIdentificacion(identificacion).
                orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));

        tarea.setUsuario(usuario);

        tareaRepository.save(tarea);

        List<LocalDate> listaDeFechas = registrarTareaDTO.getFechaInicio()
                .datesUntil(registrarTareaDTO.getFechaFin().plusDays(1))
                .toList();

        for (LocalDate fecha : listaDeFechas) {

            SeguimientoTarea seguimientoTarea = new SeguimientoTarea();

            seguimientoTarea.setTarea(tarea);
            seguimientoTarea.setFecha(fecha);
            seguimientoTarea.setEstado(Estado.ENPROCESO);

            seguimientoTareaRepository.save(seguimientoTarea);
        }

    }

    @Override
    public Page<ListarMisTareasDTO> todasMisTareas(Pageable pageable) {

        String identificacion = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByIdentificacion(identificacion).
                orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));

        Page<ListarMisTareasDTO> tareas = tareaRepository.todasMisTareas(usuario.getId(), pageable);

        for (ListarMisTareasDTO tarea : tareas) {

            // Buscar seguimientos de esta tarea
            Integer diasCompletados = seguimientoTareaRepository.findBySeguimientoTareaDiasCompletados(tarea.getId());

            // Calcular progreso
            int progreso = calcularProgresoListaPage(tarea, diasCompletados);

            tarea.setProgreso(progreso);

            //tarea.setEstado("PENDIENTE");

            if (progreso == 0) {
                tarea.setEstado("PENDIENTE");
            } else if (progreso == 100) {
                tarea.setEstado("COMPLETADA");
            } else {
                tarea.setEstado("EN_PROGRESO");
            }
        }

        return tareas;
    }

    @Override
    public AdministrarTareaDTO administrarTarea(Integer idTarea) {

        String identificacion = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByIdentificacion(identificacion).
                orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));

        AdministrarTareaDTO tareaDTO = tareaRepository.administarMiTarea(usuario.getId(), idTarea);

        // Buscar seguimientos de esta tarea
        Integer diasCompletados = seguimientoTareaRepository.findBySeguimientoTareaDiasCompletados(idTarea);

        int progreso = calcularProgreso(tareaDTO, diasCompletados);

        tareaDTO.setDiasRealizados(diasCompletados);

        tareaDTO.setCantidadDias((int) ChronoUnit.DAYS.between(tareaDTO.getFechainicio(), tareaDTO.getFechaFin()) + 1);

        tareaDTO.setProgreso(progreso);

        if (progreso == 0) {
            tareaDTO.setEstado("EN_PROGRESO");
        } else if (progreso == 100) {
            tareaDTO.setEstado("COMPLETADA");
        } else {
            tareaDTO.setEstado("EN_PROGRESO");
        }

        return tareaDTO;
    }

    @Override
    @Transactional
    public void eliminarTarea(Integer idTarea) {

        Tarea tarea = tareaRepository.findById(idTarea).
                orElseThrow(() -> new EntityNotFoundException("No existe la tarea"));

        String identificacion = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (!Objects.equals(tarea.getUsuario().getId(), usuario.getId())) {
            throw new AccessDeniedException("No tiene permiso para eliminar el tarea");
        }

        tareaRepository.delete(tarea);

    }

    private int calcularProgreso(AdministrarTareaDTO tarea, Integer diasCompletados) {

        long cantidadDiasParaRealizarTarea = ChronoUnit.DAYS.between(tarea.getFechainicio(), tarea.getFechaFin()) + 1;

        // Evitar división por cero si la tarea empieza y termina el mismo día
        if (cantidadDiasParaRealizarTarea <= 0) {
            return 0;
        }

        Integer progreso = (int) ((diasCompletados * 100) / cantidadDiasParaRealizarTarea);

        return Math.min(progreso, 100);
    }

    private int calcularProgresoListaPage(ListarMisTareasDTO tarea, Integer diasCompletados) {

        long cantidadDiasParaRealizarTarea = ChronoUnit.DAYS.between(tarea.getFechainicio(), tarea.getFechaFin()) + 1;

        // Evitar división por cero si la tarea empieza y termina el mismo día
        if (cantidadDiasParaRealizarTarea <= 0) {
            return 0;
        }

        Integer progreso = (int) ((diasCompletados * 100) / cantidadDiasParaRealizarTarea);

        return Math.min(progreso, 100);
    }

}
