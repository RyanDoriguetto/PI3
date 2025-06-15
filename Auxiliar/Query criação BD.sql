create database pi2025;
use pi2025;

create table peca(
id_peca int auto_increment primary key,
nome varchar(100) not null
);

create table area(
id_area int auto_increment primary key,
nome varchar(50) not null,
qtd_poltronas int not null,
preco int not null
);

create table horario(
id_horario int auto_increment primary key,
turno varchar(20) not null,
hora_inicio time,
hora_fim time
);

create table endereco(
id_endereco int auto_increment primary key,
rua varchar(50),
quadra varchar(50),
lote varchar(50),
bairro varchar(50),
cidade varchar(50),
estado varchar(50)
);

create table usuario(
id_usuario int auto_increment primary key,
id_endereco int not null,
nome varchar(100),
cpf varchar(11),
telefone varchar(11),
data_nasc date,
foreign key (id_endereco) references endereco(id_endereco)
);

create table sessao(
id_sessao int auto_increment primary key,
id_peca int not null,
id_horario int not null,
foreign key (id_peca) references peca(id_peca),
foreign key (id_horario) references horario(id_horario)
);

create table ingresso(
id_ingresso int auto_increment primary key,
id_usuario int not null,
id_sessao int not null,
id_area int not null,
posicaoPoltrona int not null,
valor_pago int not null,
foreign key (id_usuario) references usuario(id_usuario),
foreign key (id_sessao) references sessao(id_sessao),
foreign key (id_area) references Area(id_area)
);

INSERT INTO horario (turno, hora_inicio, hora_fim) VALUES
('Manhã', '08:00:00', '09:30:00'),
('Manhã', '09:45:00', '11:15:00'),
('Manhã', '11:30:00', '13:00:00'),

('Tarde', '13:30:00', '15:00:00'),
('Tarde', '15:15:00', '16:45:00'),
('Tarde', '17:00:00', '18:30:00'),

('Noite', '19:00:00', '20:30:00'),
('Noite', '20:45:00', '22:15:00'),
('Noite', '22:30:00', '00:00:00');

INSERT INTO peca (nome) VALUES
('Romeu e Julieta'),
('O Auto da Compadecida'),
('Chapeuzinho Vermelho');

INSERT INTO sessao (id_peca, id_horario) VALUES
(1, 1),  -- Romeu e Julieta - Manhã
(2, 2),  -- Auto da Compadecida - Manhã
(3, 3),  -- Chapeuzinho Vermelho - Manhã

(1, 4),  -- Romeu e Julieta - Tarde
(2, 5),  -- Auto da Compadecida - Tarde
(3, 6),  -- Chapeuzinho Vermelho - Tarde

(1, 7),  -- Romeu e Julieta - Noite
(2, 8),  -- Auto da Compadecida - Noite
(3, 9);  -- Chapeuzinho Vermelho - Noite
select * from area;

INSERT INTO area(nome, qtd_poltronas, preco) VALUES
('Plateia A', 25, 40),
('Plateia B', 100, 60),
('Frisa', 30, 120),
('Camarote', 50, 80),
('Balcao Nobre', 50, 250);

ALTER TABLE area
ADD COLUMN qtd_subareas INT DEFAULT 0,
ADD COLUMN assentos_por_subarea INT DEFAULT 0;

UPDATE area SET qtd_subareas = 6, assentos_por_subarea = 5 WHERE id_area = 3;
UPDATE area SET qtd_subareas = 5, assentos_por_subarea = 10 WHERE id_area = 4;

use pi2025;

ALTER TABLE ingresso MODIFY COLUMN posicaoPoltrona VARCHAR(10) NOT NULL;