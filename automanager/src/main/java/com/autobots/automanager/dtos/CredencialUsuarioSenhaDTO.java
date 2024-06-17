package com.autobots.automanager.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class CredencialUsuarioSenhaDTO {
    private Long id;
    private Date criacao;
    private Date ultimoAcesso;
    private boolean inativo;
    private String nomeUsuario;
}

