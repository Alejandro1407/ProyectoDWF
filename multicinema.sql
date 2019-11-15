create database multicinema;
use multicinema;
create table tipo(
id int primary key auto_increment,
tipo varchar(100) not null
);
create table oficio(
id int primary key auto_increment,
trabajo varchar(100) not null
);
create table usuario (
    id int primary key auto_increment,
    nombre varchar(200) not null,
    correo varchar(150)  not null unique,
    password varchar(100) not null,
    telefono varchar(10) not null,
    tipo int not null,
    direccion varchar(200),
    dui varchar(10) not null unique,
    tarjeta varchar(20) unique,
    enabled bit not null default 0
 );

 create table genero (
 id int auto_increment primary key,
 genero varchar(100) not null
 );
 create table clasificacion(
 id int auto_increment primary key,
 clasificacion varchar(100) not null
 );
 create table produccion(
 id int auto_increment primary key,
 nombre varchar(100) not null,
 oficio int not null
 );
 create table reparto(
pelicula int not null,
 actor int not null
 );
 create table director(
 pelicula int not null,
 director int not null
 );
 create table idioma(
 id int not null primary key AUTO_INCREMENT,
 idioma varchar(100) not null
 );
create table pelicula(
id int AUTO_INCREMENT primary key,
titulo varchar(200) not null,
duracion int not null,
sinopsis text not null,
subtitulada tinyint not null,
clasificacion int not null,
imagen longblob ,
estreno datetime not null
);
create table idiomaPelicula(
pelicula int not null,
idioma int not null
);
create table generoPelicula(
pelicula int not null,
genero int not null
);
create table sala(
id int primary key auto_increment,
descripcion varchar(100) null
);

create table asiento(
 columna char not null,
 fila char not null,
 id int not null primary key
);

create table asientoSala(
sala int not null,
asiento int not null
);

create table funcion(
id int primary key auto_increment,
codigo varchar(12) unique ,
pelicula int not null,
horario date not null,
sala int not null,
idioma int not null,
funcion3d boolean default 0,
horaInicio TIME not null,
horaFin TIME null
);

create table entrada(
id int primary key auto_increment,
codigo varchar(15) unique,
usuario int not null,
funcion varchar(12) not null,
asiento int not null
);


ALTER TABLE usuario
ADD FOREIGN KEY (tipo) REFERENCES tipo(id);

alter table produccion
add foreign key (oficio) references oficio(id);

alter table reparto 
add foreign key(pelicula) references pelicula(id),
add foreign key(actor) references produccion(id);

alter table director
add foreign key(pelicula) references pelicula(id),
add foreign key(director) references produccion(id);

alter table pelicula
add foreign key(clasificacion) references clasificacion(id);

alter table generoPelicula
add foreign key(pelicula) references pelicula(id),
add foreign key(genero) references genero(id);

alter table asientoSala
add foreign key(sala) references sala(id),
add foreign key(asiento) references asiento(id);

alter table funcion
add foreign key(sala) references sala(id),
add foreign key(pelicula) references pelicula(id),
add foreign key(idioma) references idioma(id);

alter table entrada
add foreign key(usuario) references usuario(id),
add foreign key(funcion) references funcion(codigo),
add foreign key(asiento) references asiento(id);
alter table idiomaPelicula
add foreign key(pelicula) references pelicula(id),
add foreign key(idioma) references idioma(id);

ALTER TABLE asientoSala ADD PRIMARY KEY ( asiento , sala );
ALTER TABLE director ADD PRIMARY KEY ( pelicula , director );
ALTER TABLE reparto ADD PRIMARY KEY ( pelicula , actor );
ALTER TABLE idiomaPelicula ADD PRIMARY KEY ( pelicula , idioma );
ALTER TABLE generoPelicula ADD PRIMARY KEY ( pelicula , genero );

delimiter //
create trigger calcularHoraF before insert on funcion
for each row
	begin
		set @minutos = (select duracion from pelicula where id = new.pelicula);
        set @horaFinal = (new.horaInicio + interval @minutos minute);
        set new.horaFin = @horaFinal;
    end//
delimiter ;

INSERT INTO `tipo` (`id`, `tipo`) VALUES 
('1', 'Administrador'), 
('2', 'Cliente'),
 ('3', 'Encargado'), 
('4', 'Dependiente');

INSERT INTO `usuario` ( `nombre`, `correo`, `password`, `telefono`, `tipo`, `direccion`, `dui`, `tarjeta`) VALUES 
('Alejandro Alejo', 'alejandroalejo714@gmail.com', SHA2('Password',256), '2222-2222', '1', 'Apopa', '12345678-9', NULL), 
('Kevin Coreas', 'kevincor13@gmail.com', SHA2('Password',256), '1234-5678', '1', 'Soyapango', '12345678-0', NULL);

INSERT INTO oficio VALUES (NULL,"Director"),
                          (NULL,"Actor");

INSERT INTO `clasificacion` (`id`, `clasificacion`) VALUES (NULL, 'TP'), (NULL, 'M-12'), (NULL, 'M-15'), (NULL, 'M-18');

INSERT INTO `genero` (`id`, `genero`) VALUES (NULL, 'Accion'), (NULL, 'Terror'), (NULL, 'Comedia'), (NULL, 'Romance'), (NULL, 'Drama'), (NULL, 'Suspenso'), (NULL, 'Ciencia ficcion'), (NULL, 'Fantasia'), (NULL, 'Musical');

INSERT INTO `idioma` (`id`, `idioma`) VALUES (NULL, 'Ingles'), (NULL, 'Español');


delimiter //
create trigger generarCodigo before insert on funcion
for each row
	begin
        set @pelicula = (select titulo from pelicula where id = new.pelicula);
        set @codigo = substring(@pelicula, 1, 3);
        set @codigo = concat(@codigo, new.id);
        set new.codigo = @codigo;
    end//
delimiter ;

select * from funcion where 
'2019-02-02' != (select horario from funcion where horaInicio = '14:00' and sala = 1 limit 1) OR
1 != (select sala from funcion where horaInicio = '14:00' and horario = '2019-02-02' limit 1) OR
'14:00' not between (select horaInicio from funcion where horario = '2019-02-02' and sala = 1 limit 1) AND
(select horaFin from funcion where horario = '2019-02-02' and sala = 1 limit 1) and
(select horaInicio + interval (select duracion from pelicula where id = 5) minute) not between
(select horaInicio from funcion where horario = '2019-02-02' and sala = 1 limit 1) AND
(select horaFin from funcion where horario = '2019-02-02' and sala = 1 limit 1);