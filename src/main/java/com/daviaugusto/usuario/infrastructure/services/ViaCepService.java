package com.daviaugusto.usuario.infrastructure.services;


import com.daviaugusto.usuario.exceptions.IllegalArgumentException;
import com.daviaugusto.usuario.infrastructure.clients.ViaCepClient;
import com.daviaugusto.usuario.infrastructure.dtos.ViaCepDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ViaCepService {

    @Autowired
    private ViaCepClient viaCepClient;


    public ViaCepDTO buscarCep(String cep){

        return viaCepClient.buscarCep(AjustarCep(cep));

    }

    public String AjustarCep(String cep){

        String cepFormatado = cep.replace(" ", "").replace("-", "");

        if(!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)){
               throw new IllegalArgumentException("O CEP contém caracteres inválidos!");
        }

        return cepFormatado;
    }


}
