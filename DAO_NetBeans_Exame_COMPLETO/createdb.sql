-- Exemplo de creación de táboa
CREATE DATABASE IF NOT EXISTS NOME_BASE_DATOS;
USE NOME_BASE_DATOS;

CREATE TABLE Produtos (
    id INT PRIMARY KEY,
    nome VARCHAR(100),
    prezo DOUBLE,
    cantidade INT
);
