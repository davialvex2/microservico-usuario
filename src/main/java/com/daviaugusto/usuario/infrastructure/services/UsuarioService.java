package com.daviaugusto.usuario.infrastructure.services;

import com.daviaugusto.usuario.exceptions.ConflictException;
import com.daviaugusto.usuario.exceptions.NotFoundException;
import com.daviaugusto.usuario.infrastructure.converter.UsuarioConverter;
import com.daviaugusto.usuario.infrastructure.dtos.EnderecoDTO;
import com.daviaugusto.usuario.infrastructure.dtos.TelefoneDTO;
import com.daviaugusto.usuario.infrastructure.dtos.UsuarioDTO;
import com.daviaugusto.usuario.infrastructure.entity.Endereco;
import com.daviaugusto.usuario.infrastructure.entity.Telefone;
import com.daviaugusto.usuario.infrastructure.entity.Usuario;
import com.daviaugusto.usuario.infrastructure.repositories.EnderecoRepository;
import com.daviaugusto.usuario.infrastructure.repositories.TelefoneRepository;
import com.daviaugusto.usuario.infrastructure.repositories.UsuarioRepository;
import com.daviaugusto.usuario.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    TelefoneRepository telefoneRepository;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        verificarEmail(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email não encontrado"));
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public void verificarEmail(String email){
        try {
            boolean existe = buscarEmail(email);
            if(existe){
                throw new ConflictException("Email já cadastrado");
            }
        }
        catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean buscarEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO usuarioDTO){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email não encontrado"));
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);
        Usuario user = usuarioConverter.atualizarUsuario(usuarioDTO, usuario);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(user));

    }

    public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(id).orElseThrow(() -> new NotFoundException("Endereço não encontrado " + id));
        Endereco endereco = enderecoRepository.save(usuarioConverter.atulizarEndereco(enderecoDTO, entity));
        return usuarioConverter.paraEnderecoDTO(endereco);
    }

    public TelefoneDTO atualizarDadosTelefone(Long id, TelefoneDTO telefoneDTO){

        Telefone telefone = telefoneRepository.findById(id).orElseThrow(() -> new NotFoundException("Telefone não encontrado " + id));
        Telefone telefoneEntity = telefoneRepository.save(usuarioConverter.atualizarTelefone(telefoneDTO, telefone));
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }

    public EnderecoDTO inserirEndereco(String token, EnderecoDTO enderecoDTO){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email não encontrado"));

        Endereco end = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(end));
    }

    public TelefoneDTO inserirTelefone(String token, TelefoneDTO telefoneDTO){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email não encontrado"));

        Telefone tel = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(tel));

    }



}

