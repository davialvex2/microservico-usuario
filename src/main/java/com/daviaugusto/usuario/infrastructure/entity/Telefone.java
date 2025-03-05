package com.daviaugusto.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telefone")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String ddd;
    @Column(name = "usuario_id")
    private Long usuario_id;

}
