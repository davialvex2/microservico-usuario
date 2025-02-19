package com.daviaugusto.usuario.infrastructure.repositories;

import com.daviaugusto.usuario.infrastructure.entity.Endereco;
import com.daviaugusto.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
