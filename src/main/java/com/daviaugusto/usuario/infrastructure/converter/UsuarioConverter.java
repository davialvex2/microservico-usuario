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
                .telefone(paraListaTelefone(usuarioDTO.getTelefone())).
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
                .telefone(paraListaTelefoneDTO(usuario.getTelefone()))
                .endereco(paraListaEnderecoDTO(usuario.getEndereco())).build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos){
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder().id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento()).
                cidade(endereco.getCidade()).
                estado(endereco.getEstado()).build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones){
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder().id(telefone.getId())
                .ddd(telefone.getDdd()).numero(telefone.getNumero()).build();
    }

    public Usuario atualizarUsuario(UsuarioDTO usuario, Usuario entity){
        return Usuario.builder().nome(usuario.getNome() != null ? usuario.getNome(): entity.getNome()).id(entity.getId())
                .email(usuario.getEmail() != null ? usuario.getEmail() : entity.getEmail())
                .senha(usuario.getSenha() != null ? usuario.getSenha() : entity.getSenha())
                .endereco(entity.getEndereco())
                .telefone(entity.getTelefone()).build();
    }

    public Endereco atulizarEndereco(EnderecoDTO enderecoDTO, Endereco endereco){
        return Endereco.builder().id(endereco.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : endereco.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : endereco.getNumero())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : endereco.getComplemento())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : endereco.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : endereco.getEstado())
                .build();
    }


    public Telefone atualizarTelefone(TelefoneDTO telefoneDTO, Telefone telefone){
        return Telefone.builder().id(telefone.getId())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefone.getDdd())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefone.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO, Long idUsuario){
        return Endereco.builder().rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero()).usuario_id(idUsuario).build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO, Long idUsuario){
        return Telefone.builder().ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero()).usuario_id(idUsuario).build();
    }

}
