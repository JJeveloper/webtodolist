package com.todolist.webtodolist.repository;

import com.todolist.webtodolist.model.dto.SeguimientoTareaDTO;
import com.todolist.webtodolist.model.entity.SeguimientoTarea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoTareaRepository extends JpaRepository<SeguimientoTarea, Integer> {

    @Query("""
            SELECT COUNT(st) FROM SeguimientoTarea st WHERE st.tarea.id =:id AND st.estado = "CUMPLIDA"
            """)
    Integer findBySeguimientoTareaDiasCompletados(@Param("id") Integer id);

    @Query("""
            SELECT NEW com.todolist.webtodolist.model.dto.SeguimientoTareaDTO(st.id, st.estado, st.fecha, st.tarea.id)
            FROM SeguimientoTarea st WHERE st.tarea.id =:idTarea
            """)
    Page<SeguimientoTareaDTO> administrarSeguimientoTarea(@Param("idTarea") Integer idTarea, Pageable pageable);
}
