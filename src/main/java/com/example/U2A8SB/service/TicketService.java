package com.example.U2A8SB.service;

import com.example.U2A8SB.dto.TicketCreateDTO;
import com.example.U2A8SB.dto.TicketResponseDTO;
import com.example.U2A8SB.entity.EstadoTicket;
import com.example.U2A8SB.entity.PrioridadTicket;
import com.example.U2A8SB.entity.Ticket;
import com.example.U2A8SB.entity.Usuario;
import com.example.U2A8SB.exception.BadRequestException;
import com.example.U2A8SB.exception.ResourceNotFoundException;
import com.example.U2A8SB.repository.TicketRepository;
import com.example.U2A8SB.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public TicketResponseDTO crearTicket(TicketCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        if (!usuario.getActivo()) {
            throw new BadRequestException("El usuario con ID " + dto.getUsuarioId() + " está inactivo y no puede crear tickets.");
        }

        Ticket ticket = new Ticket();
        ticket.setDescripcion(dto.getDescripcion());
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setPrioridad(dto.getPrioridad());
        ticket.setUsuario(usuario);

        Ticket guardado = ticketRepository.save(ticket);
        return mapToDTO(guardado);
    }

    @Transactional
    public TicketResponseDTO actualizarEstadoTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con ID: " + ticketId));

        if (ticket.getEstado() == EstadoTicket.ABIERTO) {
            ticket.setEstado(EstadoTicket.EN_PROCESO);
        } else if (ticket.getEstado() == EstadoTicket.EN_PROCESO) {
            ticket.setEstado(EstadoTicket.CERRADO);
        } else if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BadRequestException("El ticket con ID " + ticketId + " ya se encuentra CERRADO y no se puede modificar.");
        }

        Ticket actualizado = ticketRepository.save(ticket);
        return mapToDTO(actualizado);
    }

    @Transactional
    public TicketResponseDTO reasignarPrioridad(Long ticketId, PrioridadTicket nuevaPrioridad) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con ID: " + ticketId));

        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BadRequestException("No se puede modificar la prioridad de un ticket en estado CERRADO.");
        }

        ticket.setPrioridad(nuevaPrioridad);
        Ticket actualizado = ticketRepository.save(ticket);
        return mapToDTO(actualizado);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId);
        }

        return ticketRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TicketResponseDTO mapToDTO(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getDescripcion(),
                ticket.getEstado(),
                ticket.getPrioridad(),
                ticket.getUsuario().getId(),
                ticket.getUsuario().getNombre()
        );
    }
}