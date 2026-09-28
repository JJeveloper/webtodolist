package com.todolist.webtodolist.model.dto;

import com.todolist.webtodolist.model.validation.CedulaEcuador;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrarUsuarioDTO {

    @NotBlank(message = "Ingrese su cédula")
    @CedulaEcuador()
    private String identificacion;

    @NotBlank(message = "Ingrese sus nombres")
    @Size(min = 5, message = "El texto en el campo nombres es muy corto")
    private String nombres;

    @NotBlank(message = "Ingrese su contrasena")
    @Size(min = 5, message = "El texto en el campo contrasena es muy corto")
    private String contrasena;

    public RegistrarUsuarioDTO() {
    }

    public RegistrarUsuarioDTO(String identificacion, String nombres, String contrasena) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.contrasena = contrasena;
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
}
