package com.example.U2A8SB.controller;

import com.example.U2A8SB.dto.CambiarPrioridadDTO;
import com.example.U2A8SB.dto.TicketCreateDTO;
import com.example.U2A8SB.dto.TicketResponseDTO;
import com.example.U2A8SB.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> crearTicket(@Valid @RequestBody TicketCreateDTO dto) {
        TicketResponseDTO nuevoTicket = ticketService.crearTicket(dto);
        return new ResponseEntity<>(nuevoTicket, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/siguiente-estado")
    public ResponseEntity<TicketResponseDTO> actualizarEstado(@PathVariable Long id) {
        TicketResponseDTO ticketActualizado = ticketService.actualizarEstadoTicket(id);
        return ResponseEntity.ok(ticketActualizado);
    }

    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<TicketResponseDTO> reasignarPrioridad(
            @PathVariable Long id,
            @Valid @RequestBody CambiarPrioridadDTO dto) {
        TicketResponseDTO ticketActualizado = ticketService.reasignarPrioridad(id, dto.getPrioridad());
        return ResponseEntity.ok(ticketActualizado);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TicketResponseDTO>> obtenerTicketsUsuario(@PathVariable Long usuarioId) {
        List<TicketResponseDTO> tickets = ticketService.obtenerTicketsUsuario(usuarioId);
        return ResponseEntity.ok(tickets);
    }
}