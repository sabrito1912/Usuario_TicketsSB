package com.example.U2A8SB.dto;

import com.example.U2A8SB.entity.EstadoTicket;
import com.example.U2A8SB.entity.PrioridadTicket;

public class TicketResponseDTO {

    private Long id;
    private String descripcion;
    private EstadoTicket estado;
    private PrioridadTicket prioridad;
    private Long usuarioId;
    private String usuarioNombre;

    public TicketResponseDTO(Long id, String descripcion, EstadoTicket estado, PrioridadTicket prioridad, Long usuarioId, String usuarioNombre) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
    }

    public Long getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public EstadoTicket getEstado() { return estado; }
    public PrioridadTicket getPrioridad() { return prioridad; }
    public Long getUsuarioId() { return usuarioId; }
    public String getUsuarioNombre() { return usuarioNombre; }
}