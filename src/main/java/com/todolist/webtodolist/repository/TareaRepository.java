package com.todolist.webtodolist.repository;

import com.todolist.webtodolist.model.dto.AdministrarTareaDTO;
import com.todolist.webtodolist.model.dto.ListarMisTareasDTO;
import com.todolist.webtodolist.model.dto.ListarTareasPublicasDTO;
import com.todolist.webtodolist.model.entity.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    @Query("""
                    SELECT NEW com.todolist.webtodolist.model.dto.ListarMisTareasDTO(t.id, t.tarea, t.descripcion, t.visibilidad, t.fechainicio, t.fechafin)
                    FROM Tarea t WHERE t.usuario.id = :id
            """)
    Page<ListarMisTareasDTO> todasMisTareas(@Param("id") Integer id, Pageable pageable);

    @Query("""
                    SELECT NEW com.todolist.webtodolist.model.dto.AdministrarTareaDTO(t.id, t.tarea, t.descripcion, t.visibilidad, t.fechainicio, t.fechafin)
                    FROM Tarea t WHERE t.usuario.id = :idUsuario AND t.id = :idTarea
            """)
    AdministrarTareaDTO administarMiTarea(@Param("idUsuario") Integer idUsuario, @Param("idTarea") Integer idTarea);

    @Query("""
                    SELECT NEW com.todolist.webtodolist.model.dto.ListarTareasPublicasDTO(t.id, t.tarea, t.descripcion, t.visibilidad, t.fechainicio, t.fechafin, u.nombres)
                    FROM Tarea t JOIN t.usuario u WHERE t.visibilidad = "PUBLICA"
            """)
    Page<ListarTareasPublicasDTO> tareasPublicas(Pageable pageable);

}
