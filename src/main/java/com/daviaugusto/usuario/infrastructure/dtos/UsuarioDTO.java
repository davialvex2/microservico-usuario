package com.daviaugusto.usuario.infrastructure.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {


    private String nome;
    private String email;
    private String senha;
    private List<TelefoneDTO> telefone;
    private List<EnderecoDTO> endereco;



}
