package com.daviaugusto.usuario.infrastructure.converter;

import com.daviaugusto.usuario.infrastructure.dtos.EnderecoDTO;
import com.daviaugusto.usuario.infrastructure.dtos.TelefoneDTO;
import com.daviaugusto.usuario.infrastructure.dtos.UsuarioDTO;
import com.daviaugusto.usuario.infrastructure.entity.Endereco;
import com.daviaugusto.usuario.infrastructure.entity.Telefone;
import com.daviaugusto.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder().nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .endereco(paraListaEndereco(usuarioDTO.getEndereco()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefone())).
        build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTO){
        return enderecoDTO.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder().rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .cidade(enderecoDTO.getCidade()).build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTO){
        return telefoneDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder().ddd(telefoneDTO.getDdd()).numero(telefoneDTO.getNumero()).build();
    }


    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder().nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .telefone(paraListaTelefoneDTO(usuario.getTelefones()))
                .endereco(paraListaEnderecoDTO(usuario.getEndereco())).build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos){
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder().rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento()).
                cidade(endereco.getCidade()).
                estado(endereco.getEstado()).build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones){
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder().ddd(telefone.getDdd()).numero(telefone.getNumero()).build();
    }


}
