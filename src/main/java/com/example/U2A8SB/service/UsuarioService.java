package com.example.U2A8SB.service;

import com.example.U2A8SB.dto.UsuarioCreateDTO;
import com.example.U2A8SB.entity.Usuario;
import com.example.U2A8SB.exception.ResourceNotFoundException;
import com.example.U2A8SB.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario crearUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario(dto.getNombre());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void desactivarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}