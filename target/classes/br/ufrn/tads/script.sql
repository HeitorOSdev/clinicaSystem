--Login--
--Usuario:postgres
--Senha:admin

--port do postgresql server (desse sistema):
-- 5432

--Comando para criar a database--
CREATE DATABASE clinicadb TEMPLATE template1

--Comando para criar as tabelas--
CREATE TABLE IF NOT EXISTS Medico (
  id SERIAL PRIMARY KEY,
  crm VARCHAR(20) UNIQUE NOT NULL,
  nome VARCHAR(100) NOT NULL,
  especialidade VARCHAR(100),
  telefone VARCHAR(20),
  email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Paciente (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100),
  cpf VARCHAR(20),
  dataNascimento DATE,
  genero VARCHAR(10),
  telefone VARCHAR(20),
  email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Consulta (
  id SERIAL PRIMARY KEY,
  fkPaciente INTEGER,
  fkMedico INTEGER,
  data DATE,
  queixa VARCHAR(1000),
  descricao VARCHAR(1000),
  relatosClinicos VARCHAR(10000),
  FOREIGN KEY (fkPaciente) REFERENCES Paciente(id),
  FOREIGN KEY (fkMedico) REFERENCES Medico(id)
);

--ISERTS INTO TABELAS() ..........

