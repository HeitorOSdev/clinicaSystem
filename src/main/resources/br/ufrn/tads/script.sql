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

INSERT INTO Medico(crm, nome, especialidade, telefone, email) VALUES --deveria ter um status desativado aqui... para que ao inves de apagar, ele seja desativado e as info da de Consulta persistam
('123456/RN', 'Heitor Santos', 'Cardiologista', '(84)91111-2222', 'heitor@email'),
('789101/RN', 'Hugo Cassiano', 'Pediatria', '(84)92222-3333', 'hugo@email'),
('121314/RN', 'Renato Cariani', 'Ortopedia', '(84)93333-4444', 'renato@email'),
('151617/RN', 'Paulo Muzy', 'Neurologia', '(84)94444-5555', 'paulo_muzy@email'),
('654321/RN', 'Paulo Freire', 'Dermatologia', '(84)95555-6666', 'paulo_freire@email'),
('098765/RN', 'Marcos Daniel', 'Dermatologia', '(84)96666-7777', 'marcos@email'),
('765987/RN', 'Daniel Santos', 'Endocrinologia', '(84)97777-8888', 'daniel@email'),
('321456/SP', 'Sanji Perene', 'Gastroenterologia', '(84)98888-9999', 'sanji@email'),
('654123/RS', 'Helena Silva', 'Ginecologia', '(84)99999-0000', 'helena@email'),
('789987/RJ', 'Serena Ramos', 'Hematologia', '(84)90101-0202', 'serena@email');

INSERT INTO Paciente(nome, cpf, dataNascimento, genero, telefone, email), VALUES
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email'),
('nome', 'cpf', 'dataNascimento', 'genero', 'telefone', 'email');

INSERT INTO Consulta(data, queixa, descricao, relatosClinicos) VALUES
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos'),
('data', 'queixa', 'descricao', 'relatosClinicos');

