package com.autobots.automanager.dtos;

import lombok.Data;

@Data
public class ServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String codigo;
    private double preco;
}

