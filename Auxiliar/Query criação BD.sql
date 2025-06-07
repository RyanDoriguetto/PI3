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

