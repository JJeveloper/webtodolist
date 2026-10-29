package com.todolist.webtodolist.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtareas", nullable = false)
    private Integer id;

    @Size(max = 500)
    @NotNull
    @Column(name = "tarea", nullable = false, length = 500)
    private String tarea;

    @Size(max = 3000)
    @NotNull
    @Column(name = "descripcion", nullable = false, length = 3000)
    private String descripcion;

    @NotNull
    @Column(name = "fechainicio", nullable = false)
    private LocalDate fechainicio;

    @NotNull
    @Column(name = "fechafin", nullable = false)
    private LocalDate fechafin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibilidad", nullable = false)
    private Visibilidad visibilidad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usurios_idusurios")
    private Usuario usuario;

    @OneToMany(mappedBy = "tarea")
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeguimientoTarea> seguimientoTareas = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public LocalDate getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(LocalDate fechainicio) {
        this.fechainicio = fechainicio;
    }

    public LocalDate getFechafin() {
        return fechafin;
    }

    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }

    public Visibilidad getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(Visibilidad visibilidad) {
        this.visibilidad = visibilidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<SeguimientoTarea> getSeguimientotareas() {
        return seguimientoTareas;
    }

    public void setSeguimientotareas(List<SeguimientoTarea> seguimientoTareas) {
        this.seguimientoTareas = seguimientoTareas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}