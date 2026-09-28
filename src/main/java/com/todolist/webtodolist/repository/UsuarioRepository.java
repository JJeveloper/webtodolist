package com.todolist.webtodolist.repository;

import com.todolist.webtodolist.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("""
            SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.identificacion = :identificacion
            """)
    Optional<Usuario> findByIdentificacion(String identificacion);


}
