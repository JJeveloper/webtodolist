package com.todolist.webtodolist.service.interfaces;

import com.todolist.webtodolist.model.dto.SeguimientoTareaDTO;
import com.todolist.webtodolist.model.entity.Estado;
import com.todolist.webtodolist.model.entity.SeguimientoTarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SeguimientoTareaService {

    Page<SeguimientoTareaDTO> listarSeguimientoTarea(Integer idTarea, Pageable pageable);

    Map<String, Integer>  controlDiasTarea(Integer idSeguimiento, Estado estado);

}
