package com.todolist.webtodolist.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuarios", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "identificacion", nullable = false, length = 150)
    private String identificacion;

    @Size(max = 250)
    @NotNull
    @Column(name = "nombres", nullable = false, length = 250)
    private String nombres;

    @Size(max = 500)
    @NotNull
    @Column(name = "contrasena", nullable = false, length = 500)
    private String contrasena;

    @NotNull
    @Column(name = "fechaderegistro", nullable = false)
    private LocalDateTime fechaderegistro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_idroles")
    private Rol rol;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "usuario")
    private List<Tarea> tareas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDateTime getFechaderegistro() {
        return fechaderegistro;
    }

    public void setFechaderegistro(LocalDateTime fechaderegistro) {
        this.fechaderegistro = fechaderegistro;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas( List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}