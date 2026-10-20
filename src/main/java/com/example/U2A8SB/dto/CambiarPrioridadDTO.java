package com.example.U2A8SB.dto;

import com.example.U2A8SB.entity.PrioridadTicket;
import jakarta.validation.constraints.NotNull;

public class CambiarPrioridadDTO {

    @NotNull(message = "La nueva prioridad es obligatoria.")
    private PrioridadTicket prioridad;

    public PrioridadTicket getPrioridad() { return prioridad; }
    public void setPrioridad(PrioridadTicket prioridad) { this.prioridad = prioridad; }
}