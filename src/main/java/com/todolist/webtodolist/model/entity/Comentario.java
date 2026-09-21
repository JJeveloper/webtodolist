package com.todolist.webtodolist.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomentarios", nullable = false)
    private Integer id;

    @Size(max = 3000)
    @NotNull
    @Column(name = "comentario", nullable = false, length = 3000)
    private String comentario;

    @NotNull
    @Column(name = "fechacomentario", nullable = false)
    private LocalDate fechacomentario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tareas_idtareas", nullable = false)
    private Tarea tarea;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usurios_idusurios")
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFechacomentario() {
        return fechacomentario;
    }

    public void setFechacomentario(LocalDate fechacomentario) {
        this.fechacomentario = fechacomentario;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}