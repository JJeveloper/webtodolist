package com.todolist.webtodolist.model.dto;

import com.todolist.webtodolist.model.entity.Estado;

import java.time.LocalDate;

public class SeguimientoTareaDTO {

    private Integer id;
    private Estado estado;
    private LocalDate fecha;
    private Integer idTarea;

    public SeguimientoTareaDTO(Integer id, Estado estado, LocalDate fecha, Integer idTarea) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.idTarea = idTarea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }
}
