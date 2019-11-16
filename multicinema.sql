-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-11-2019 a las 04:14:12
-- Versión del servidor: 10.1.40-MariaDB
-- Versión de PHP: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `multicinema`
--

CREATE DATABASE multicinema;

USE multicinema;

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `loguearse` (IN `correo` VARCHAR(150), IN `password` VARCHAR(50))  begin
        SELECT u.*, t.*
        FROM usuario u
        INNER JOIN tipo t
        ON u.tipo = t.id
        WHERE (u.correo = correo AND u.password = SHA2(password,256)); 
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asiento`
--

CREATE TABLE `asiento` (
  `columna` char(1) NOT NULL,
  `fila` char(1) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asiento`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asientosala`
--

CREATE TABLE `asientosala` (
  `sala` int(11) NOT NULL,
  `asiento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asientosala`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clasificacion`
--

CREATE TABLE `clasificacion` (
  `id` int(11) NOT NULL,
  `clasificacion` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clasificacion`
--

INSERT INTO `clasificacion` (`id`, `clasificacion`) VALUES
(3, 'AA'),
(4, 'A'),
(5, 'B'),
(6, 'B15'),
(7, 'C'),
(8, 'D');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `director`
--

CREATE TABLE `director` (
  `pelicula` int(11) NOT NULL,
  `director` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `director`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrada`
--

CREATE TABLE `entrada` (
  `id` int(11) NOT NULL,
  `codigo` varchar(15) DEFAULT NULL,
  `usuario` int(11) NOT NULL,
  `funcion` varchar(12) NOT NULL,
  `asiento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcion`
--

CREATE TABLE `funcion` (
  `id` int(11) NOT NULL,
  `codigo` varchar(12) DEFAULT NULL,
  `pelicula` int(11) NOT NULL,
  `horario` date NOT NULL,
  `sala` int(11) NOT NULL,
  `idioma` int(11) NOT NULL,
  `funcion3d` tinyint(1) DEFAULT '0',
  `horaInicio` time NOT NULL,
  `horaFin` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `funcion`
--
DELIMITER $$
CREATE TRIGGER `insertarFuncion` BEFORE INSERT ON `funcion` FOR EACH ROW begin
		set @minutos = (select duracion from pelicula where id = new.pelicula);
        set @horaFinal = (new.horaInicio + interval @minutos minute);
        
		set @pelicula = (select titulo from pelicula where id = new.pelicula);
        set @pelicula = replace(@pelicula, ' ', '');
        set @pelicula = upper(@pelicula);
        set @codigo = substring(@pelicula, 1, 3);
        set @incremento = (select `AUTO_INCREMENT`
			FROM  INFORMATION_SCHEMA.TABLES
			WHERE TABLE_SCHEMA = 'multicinema'
			AND   TABLE_NAME   = 'funcion');

        set @codigo = concat(@codigo, @incremento);
        set new.horaFin = @horaFinal;
        set new.codigo = @codigo;
    end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero`
--

CREATE TABLE `genero` (
  `id` int(11) NOT NULL,
  `genero` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `genero`
--

INSERT INTO `genero` (`id`, `genero`) VALUES
(5, 'Drama'),
(6, 'Suspenso'),
(8, 'Fantasia'),
(9, 'Musical'),
(13, 'Adultos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `generopelicula`
--

CREATE TABLE `generopelicula` (
  `pelicula` int(11) NOT NULL,
  `genero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idioma`
--

CREATE TABLE `idioma` (
  `id` int(11) NOT NULL,
  `idioma` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `idioma`
--

INSERT INTO `idioma` (`id`, `idioma`) VALUES
(1, 'Ingles'),
(2, 'Frances'),
(3, 'Español');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idiomapelicula`
--

CREATE TABLE `idiomapelicula` (
  `pelicula` int(11) NOT NULL,
  `idioma` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `idiomapelicula`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oficio`
--

CREATE TABLE `oficio` (
  `id` int(11) NOT NULL,
  `trabajo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `oficio`
--

INSERT INTO `oficio` (`id`, `trabajo`) VALUES
(1, 'Director'),
(2, 'Actor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pelicula`
--

CREATE TABLE `pelicula` (
  `id` int(11) NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `duracion` int(11) NOT NULL,
  `sinopsis` text NOT NULL,
  `subtitulada` tinyint(4) NOT NULL,
  `clasificacion` int(11) NOT NULL,
  `imagen` longblob,
  `estreno` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pelicula`
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `oficio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id`, `nombre`, `oficio`) VALUES
(4, 'Dennise', 2),
(6, 'Burns', 2),
(8, 'Dross', 2),
(9, 'Lisseth', 2),
(11, 'Marco', 2),
(12, 'Guillermo del Toro', 1),
(13, 'Chispother Nolan', 1),
(14, 'Woody Allen', 1),
(15, 'Tim Burton', 1),
(16, 'Charles Chaplin', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reparto`
--

CREATE TABLE `reparto` (
  `pelicula` int(11) NOT NULL,
  `actor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sala`
--

CREATE TABLE `sala` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sala`
--

INSERT INTO `sala` (`id`, `descripcion`) VALUES
(1, 'Sala 1'),
(2, 'Sala 2'),
(3, 'Sala 3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE `tipo` (
  `id` int(11) NOT NULL,
  `tipo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo`
--

INSERT INTO `tipo` (`id`, `tipo`) VALUES
(1, 'Administrador'),
(2, 'Cliente'),
(3, 'Encargado'),
(4, 'Dependiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `token`
--

CREATE TABLE `token` (
  `id` int(11) NOT NULL,
  `token` varchar(50) NOT NULL,
  `email` varchar(60) NOT NULL,
  `valid` bit(1) DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `token`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `correo` varchar(150) NOT NULL,
  `password` varchar(200) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `tipo` int(11) NOT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `dui` varchar(10) NOT NULL,
  `tarjeta` varchar(20) DEFAULT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `correo`, `password`, `telefono`, `tipo`, `direccion`, `dui`, `tarjeta`, `enabled`) VALUES
(20, 'Alejandro Alejo', 'alejandroalejo715@gmail.com', 'e7cf3ef4f17c3999a94f2c6f612e8a888e5b1026878e4e19398b23bd38ec221a', '2222-2222', 1, 'Apopa', '12345678-9', NULL, b'1'),
(21, 'Kevin Coreas', 'alejandroalejo716@gmail.com', 'e7cf3ef4f17c3999a94f2c6f612e8a888e5b1026878e4e19398b23bd38ec221a', '1234-5678', 3, 'Soyapango', '12345678-0', NULL, b'1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asiento`
--
ALTER TABLE `asiento`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `asientosala`
--
ALTER TABLE `asientosala`
  ADD PRIMARY KEY (`asiento`,`sala`),
  ADD KEY `sala` (`sala`);

--
-- Indices de la tabla `clasificacion`
--
ALTER TABLE `clasificacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `director`
--
ALTER TABLE `director`
  ADD PRIMARY KEY (`pelicula`,`director`),
  ADD KEY `director` (`director`);

--
-- Indices de la tabla `entrada`
--
ALTER TABLE `entrada`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo` (`codigo`),
  ADD KEY `usuario` (`usuario`),
  ADD KEY `funcion` (`funcion`),
  ADD KEY `asiento` (`asiento`);

--
-- Indices de la tabla `funcion`
--
ALTER TABLE `funcion`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo` (`codigo`);

--
-- Indices de la tabla `genero`
--
ALTER TABLE `genero`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `generopelicula`
--
ALTER TABLE `generopelicula`
  ADD PRIMARY KEY (`pelicula`,`genero`),
  ADD KEY `genero` (`genero`);

--
-- Indices de la tabla `idioma`
--
ALTER TABLE `idioma`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `idiomapelicula`
--
ALTER TABLE `idiomapelicula`
  ADD PRIMARY KEY (`pelicula`,`idioma`),
  ADD KEY `idioma` (`idioma`);

--
-- Indices de la tabla `oficio`
--
ALTER TABLE `oficio`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD PRIMARY KEY (`id`),
  ADD KEY `clasificacion` (`clasificacion`);

--
-- Indices de la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oficio` (`oficio`);

--
-- Indices de la tabla `reparto`
--
ALTER TABLE `reparto`
  ADD PRIMARY KEY (`pelicula`,`actor`),
  ADD KEY `actor` (`actor`);

--
-- Indices de la tabla `sala`
--
ALTER TABLE `sala`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipo`
--
ALTER TABLE `tipo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `correo` (`correo`),
  ADD UNIQUE KEY `dui` (`dui`),
  ADD UNIQUE KEY `tarjeta` (`tarjeta`),
  ADD KEY `tipo` (`tipo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clasificacion`
--
ALTER TABLE `clasificacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `entrada`
--
ALTER TABLE `entrada`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `funcion`
--
ALTER TABLE `funcion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `genero`
--
ALTER TABLE `genero`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `idioma`
--
ALTER TABLE `idioma`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `oficio`
--
ALTER TABLE `oficio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `pelicula`
--
ALTER TABLE `pelicula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `tipo`
--
ALTER TABLE `tipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `token`
--
ALTER TABLE `token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asientosala`
--
ALTER TABLE `asientosala`
  ADD CONSTRAINT `asientosala_ibfk_1` FOREIGN KEY (`sala`) REFERENCES `sala` (`id`),
  ADD CONSTRAINT `asientosala_ibfk_2` FOREIGN KEY (`asiento`) REFERENCES `asiento` (`id`);

--
-- Filtros para la tabla `director`
--
ALTER TABLE `director`
  ADD CONSTRAINT `director_ibfk_1` FOREIGN KEY (`pelicula`) REFERENCES `pelicula` (`id`),
  ADD CONSTRAINT `director_ibfk_2` FOREIGN KEY (`director`) REFERENCES `produccion` (`id`);

--
-- Filtros para la tabla `entrada`
--
ALTER TABLE `entrada`
  ADD CONSTRAINT `entrada_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `entrada_ibfk_2` FOREIGN KEY (`funcion`) REFERENCES `funcion` (`codigo`),
  ADD CONSTRAINT `entrada_ibfk_3` FOREIGN KEY (`asiento`) REFERENCES `asiento` (`id`);

--
-- Filtros para la tabla `generopelicula`
--
ALTER TABLE `generopelicula`
  ADD CONSTRAINT `generopelicula_ibfk_1` FOREIGN KEY (`pelicula`) REFERENCES `pelicula` (`id`),
  ADD CONSTRAINT `generopelicula_ibfk_2` FOREIGN KEY (`genero`) REFERENCES `genero` (`id`);

--
-- Filtros para la tabla `idiomapelicula`
--
ALTER TABLE `idiomapelicula`
  ADD CONSTRAINT `idiomapelicula_ibfk_1` FOREIGN KEY (`pelicula`) REFERENCES `pelicula` (`id`),
  ADD CONSTRAINT `idiomapelicula_ibfk_2` FOREIGN KEY (`idioma`) REFERENCES `idioma` (`id`),
  ADD CONSTRAINT `idiomapelicula_ibfk_3` FOREIGN KEY (`pelicula`) REFERENCES `pelicula` (`id`),
  ADD CONSTRAINT `idiomapelicula_ibfk_4` FOREIGN KEY (`idioma`) REFERENCES `idioma` (`id`);

--
-- Filtros para la tabla `pelicula`
--
ALTER TABLE `pelicula`
  ADD CONSTRAINT `pelicula_ibfk_1` FOREIGN KEY (`clasificacion`) REFERENCES `clasificacion` (`id`);

--
-- Filtros para la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD CONSTRAINT `produccion_ibfk_1` FOREIGN KEY (`oficio`) REFERENCES `oficio` (`id`);

--
-- Filtros para la tabla `reparto`
--
ALTER TABLE `reparto`
  ADD CONSTRAINT `reparto_ibfk_1` FOREIGN KEY (`pelicula`) REFERENCES `pelicula` (`id`),
  ADD CONSTRAINT `reparto_ibfk_2` FOREIGN KEY (`actor`) REFERENCES `produccion` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`tipo`) REFERENCES `tipo` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
