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

INSERT INTO Paciente(nome, cpf, dataNascimento, genero, telefone, email) VALUES
('Heitor Felipe', '111.222.333-11', '2005-08-06', 'M', '(84)90000-0000', 'heitorfelipe@email'),
('Cassio de Prata', '222.333.444-12', '2020-10-02', 'M', '(84)90000-0001', 'cassio@email'),
('Nilce Campos', '333.444.555-13', '1992-10-30', 'F', '(84)90000-0010', 'nil@email'),
('Leonardo Moreto', '444.555.666-14', '1991-02-15', 'M', '(84)90000-0011', 'leon@email'),
('Luke de Caxias', '555.666.777-11', '1986-08-17', 'M', '(84)90000-0100', 'luke@email'),
('Susana Gloria', '666.777.888-15', '1988-12-25', 'F', '(84)90000-0101', 'susana@email'),
('Valeska Silva', '777.888.999-12', '2002-09-21', 'F', '(84)90000-0110', 'valesk@email'),
('Silvio Lima', '101.202.303-21', '2004-06-08', 'M', '(84)90000-0111', 'silvio@email'),
('Saulo de Tarso', '202.303.404-32', '2006-01-28', 'M', '(84)90000-1000', 'saulo@email'),
('Pedro Carvalho', '303.404.505-43', '2000-05-31', 'M', '(84)90000-1001', 'pedro@email');

INSERT INTO Consulta(fkPaciente, fkMedico, data, queixa, descricao, relatosClinicos) VALUES
(1, 1, '2024-08-06', 'Dor no peito', 'Pressao alta e possibilidade de entupimento das veias', 'exame de sangue'),
(1, 2, '2024-09-16', 'Dor de cabeca', 'Pressao alta, paciente carrega peso alto demais na academia', 'nenhum'),
(2, 3, '2024-09-23', 'Espirrando muito', 'Garganta inflamada', 'nenhum'),
(3, 9, '2024-10-09', 'Dores', 'Possibilidade de inflamacao', 'nenhum'),
(3, 5, '2024-11-17', 'Pele fina e fraca', 'descricao', 'Exame de sangue'),
(5, 6, '2024-12-18', 'Coceira', 'descricao', 'nenhum'),
(6, 6, '2025-01-03', 'Coceira no couro cabeludo', 'descricao', 'nenhum'),
(7, 8, '2025-01-10', 'Dor de barriga', 'Dieta muito acida', 'nenhum'),
(1, 10, '2025-01-15', 'Tontura', 'Baixo teor de ferro no sangue', 'Exame de sangue'),
(8, 10, '2025-01-17', 'Tontura', 'Possibilidade de formacao ruim dos globulos vermelhos', 'nenhum');

