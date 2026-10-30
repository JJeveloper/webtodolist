package com.todolist.webtodolist.service.interfaces;

import com.todolist.webtodolist.model.dto.AdministrarTareaDTO;
import com.todolist.webtodolist.model.dto.ListarMisTareasDTO;
import com.todolist.webtodolist.model.dto.ListarTareasPublicasDTO;
import com.todolist.webtodolist.model.dto.RegistrarTareaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TareaService {

    void guardarTarea(RegistrarTareaDTO registrarTareaDTO);

    Page<ListarMisTareasDTO> todasMisTareas(Pageable pageable);

    AdministrarTareaDTO administrarTarea(Integer idTarea);

    void eliminarTarea(Integer idTarea);

    Page<ListarTareasPublicasDTO> mostrarTodasLasTareasPublicas(Pageable pageable);

}
