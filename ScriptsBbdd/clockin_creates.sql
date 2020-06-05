SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `clockin` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `clockin`;

DROP TABLE IF EXISTS `empleado`;
CREATE TABLE `empleado` (
  `id_emp` varchar(32) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido1` varchar(45) NOT NULL,
  `apellido2` varchar(45) DEFAULT NULL,
  `dni` varchar(9) NOT NULL,
  `imagen` varchar(150) DEFAULT NULL,
  `jefe` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `jornada`;
CREATE TABLE `jornada` (
  `id_jornada` int(11) NOT NULL,
  `tipo` varchar(4) DEFAULT NULL,
  `ubicacion` varchar(45) DEFAULT NULL,
  `hora` varchar(50) DEFAULT NULL,
  `nota` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `registro_empleados`;
CREATE TABLE `registro_empleados` (
  `empleado_id_emp` varchar(32) NOT NULL,
  `fecha_alta` varchar(50) NOT NULL,
  `fecha_baja` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `registro_jornada`;
CREATE TABLE `registro_jornada` (
  `fecha` varchar(50) NOT NULL,
  `empleado_id_emp` varchar(32) NOT NULL,
  `jornada_id_jornada` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id_emp`);

ALTER TABLE `jornada`
  ADD PRIMARY KEY (`id_jornada`);

ALTER TABLE `registro_empleados`
  ADD PRIMARY KEY (`empleado_id_emp`);

ALTER TABLE `registro_jornada`
  ADD PRIMARY KEY (`fecha`,`empleado_id_emp`,`jornada_id_jornada`),
  ADD KEY `fk_empleado_has_jornada_jornada1_idx` (`jornada_id_jornada`),
  ADD KEY `fk_empleado_has_jornada_empleado1_idx` (`empleado_id_emp`);


ALTER TABLE `jornada`
  MODIFY `id_jornada` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `registro_empleados`
  ADD CONSTRAINT `fk_registro_empleados_empleado` FOREIGN KEY (`empleado_id_emp`) REFERENCES `empleado` (`id_emp`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `registro_jornada`
  ADD CONSTRAINT `fk_empleado_has_jornada_empleado1` FOREIGN KEY (`empleado_id_emp`) REFERENCES `empleado` (`id_emp`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_empleado_has_jornada_jornada1` FOREIGN KEY (`jornada_id_jornada`) REFERENCES `jornada` (`id_jornada`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;