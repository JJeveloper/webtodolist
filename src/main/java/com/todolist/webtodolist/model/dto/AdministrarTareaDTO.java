package com.todolist.webtodolist.model.dto;

import com.todolist.webtodolist.model.entity.Visibilidad;

import java.time.LocalDate;

public class AdministrarTareaDTO {

    private Integer id;

    private String tarea;

    private String descripcion;

    private Visibilidad visibilidad;

    private LocalDate fechainicio;

    private LocalDate fechaFin;

    private int progreso;

    private String estado;

    private int DiasRealizados;

    private int cantidadDias;

    public AdministrarTareaDTO(Integer id, String tarea, String descripcion, Visibilidad visibilidad, LocalDate fechainicio, LocalDate fechaFin) {
        this.id = id;
        this.tarea = tarea;
        this.descripcion = descripcion;
        this.visibilidad = visibilidad;
        this.fechainicio = fechainicio;
        this.fechaFin = fechaFin;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Visibilidad getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(Visibilidad visibilidad) {
        this.visibilidad = visibilidad;
    }

    public LocalDate getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(LocalDate fechainicio) {
        this.fechainicio = fechainicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getDiasRealizados() {
        return DiasRealizados;
    }

    public void setDiasRealizados(int diasRealizados) {
        DiasRealizados = diasRealizados;
    }

    public int getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(int cantidadDias) {
        this.cantidadDias = cantidadDias;
    }
}
