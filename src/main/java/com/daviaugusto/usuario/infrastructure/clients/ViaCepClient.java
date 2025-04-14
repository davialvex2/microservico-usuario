package com.daviaugusto.usuario.infrastructure.clients;


import com.daviaugusto.usuario.infrastructure.dtos.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "buscarcep", url = "${cep.url}")
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json")
    ViaCepDTO buscarCep(@RequestParam("cep") String email);
}
