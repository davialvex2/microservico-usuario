package com.daviaugusto.usuario.infrastructure.services;

import com.daviaugusto.usuario.infrastructure.converter.UsuarioConverter;
import com.daviaugusto.usuario.infrastructure.dtos.UsuarioDTO;
import com.daviaugusto.usuario.infrastructure.entity.Usuario;
import com.daviaugusto.usuario.infrastructure.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioConverter usuarioConverter;


    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }


}
