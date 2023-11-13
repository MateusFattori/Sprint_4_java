package br.com.fiap.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Bicicleta {

    private Long id;

    private String nunmeroDeSerie;

    private String marca;

    private String modelo;

    private String tipo;

    private String cor;

    private float valor;

    private Cliente cliente;


}
