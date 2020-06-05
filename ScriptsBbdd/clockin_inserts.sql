SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `clockin` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `clockin`;

INSERT INTO `empleado` (`id_emp`, `nombre`, `apellido1`, `apellido2`, `dni`, `imagen`, `jefe`) VALUES
('5ribxtKSsBZDdv4SwmimNPXhDMg1', 'Tony', 'Stark', NULL, '78564515U', NULL, 1),
('a', 'a', 'a', 'a', '78564515U', NULL, 0),
('b', 'Peter', 'Parker', 'b', '65987987K', NULL, 1),
('iNYqaiYq1rPjDQSQgrVfveIeNuU2', 'Steve', 'Rogers', 'Capitan America', '16089317N', NULL, 0),
('s52PtL9JeId7RMi0fySdvyDPsT63', 'Andrea', 'Ortín', 'Martín', '75933840F', NULL, 0);

INSERT INTO `jornada` (`id_jornada`, `tipo`, `ubicacion`, `hora`, `nota`) VALUES
(1, '0', 'Taller Focus', '15:57:36', 'Intento7'),
(2, '1', 'Taller', '15:00:06', 'nota 2'),
(5, '0', 'Taller', '12:13:14', 'Nota 3'),
(6, '0', 'Taller', '15:47:20', 'Intento12');

INSERT INTO `registro_empleados` (`empleado_id_emp`, `fecha_alta`, `fecha_baja`) VALUES
('5ribxtKSsBZDdv4SwmimNPXhDMg1', '15-05-2020', NULL),
('a', '15-05-2020', '30-05-2020'),
('b', '30-05-2020', NULL),
('iNYqaiYq1rPjDQSQgrVfveIeNuU2', '15-05-2020', NULL);

INSERT INTO `registro_jornada` (`fecha`, `empleado_id_emp`, `jornada_id_jornada`) VALUES
('02-06-2020', '5ribxtKSsBZDdv4SwmimNPXhDMg1', 1),
('03-06-2020', '5ribxtKSsBZDdv4SwmimNPXhDMg1', 2),
('03-06-2020', '5ribxtKSsBZDdv4SwmimNPXhDMg1', 5);
COMMIT;
