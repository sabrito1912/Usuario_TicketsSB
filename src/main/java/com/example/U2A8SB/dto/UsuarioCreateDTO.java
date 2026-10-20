package com.example.U2A8SB.dto;

import jakarta.validation.constraints.NotBlank;

public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre del usuario no puede estar vacío.")
    private String nombre;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}