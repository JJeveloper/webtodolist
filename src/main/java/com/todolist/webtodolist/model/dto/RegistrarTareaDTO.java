package com.todolist.webtodolist.model.dto;

import com.todolist.webtodolist.model.entity.Visibilidad;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegistrarTareaDTO {

    @NotBlank(message = "Ingrese un titulo para su tarea")
    @Size(min = 5, message = "El texto en el campo tarea es muy corto")
    private String tarea;

    @NotBlank(message = "Ingrese una descripcion")
    @Size(min = 5, message = "El texto en el campo descripcion es muy corto")
    private String descripcion;

    @NotNull(message = "Seleccione una fecha de inicio")
    @FutureOrPresent(message = "La fecha de inicio no puede estar en el pasado")
    private LocalDate fechaInicio;

    @NotNull(message = "Seleccione una fecha de fin")
    @Future(message = "La fecha de finalización debe ser futura")
    private LocalDate fechaFin;


    private Visibilidad visibilidad;

    public RegistrarTareaDTO() {
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Visibilidad getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(Visibilidad visibilidad) {
        this.visibilidad = visibilidad;
    }
}
