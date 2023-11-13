package br.com.fiap.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Cliente {

    private Long id;

    private String nome;

    private String CPF;

    private String nascimento;

    private String email;

    private String telefone;

}
