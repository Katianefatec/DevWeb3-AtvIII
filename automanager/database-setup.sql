drop database if exists autobots;
create database autobots;
use autobots;
CREATE TABLE Cliente (
    id BIGINT AUTO_INCREMENT,
    nome VARCHAR(255),
    nomeSocial VARCHAR(255),
    dataNascimento DATE,
    dataCadastro DATE,
    PRIMARY KEY (id)
);

CREATE TABLE Documento (
    id BIGINT AUTO_INCREMENT,
    tipo VARCHAR(255),
    numero VARCHAR(255),
    cliente_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE Endereco (
    id BIGINT AUTO_INCREMENT,
    estado VARCHAR(255),
    cidade VARCHAR(255),
    bairro VARCHAR(255),
    rua VARCHAR(255),
    numero VARCHAR(10),
    codigoPostal VARCHAR(10),
    informacoesAdicionais VARCHAR(255),
    cliente_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE Telefone (
    id BIGINT AUTO_INCREMENT,
    ddd VARCHAR(3),
    numero VARCHAR(10),
    cliente_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);