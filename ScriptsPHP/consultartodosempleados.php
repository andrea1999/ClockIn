<?PHP
$hostname ="localhost";
$database ="clockin";
$username ="root";
$password ="";

$json=array();

	$conexion = mysqli_connect($hostname,$username,$password,$database);

		$consulta="select e.id_emp, nombre, apellido1, apellido2, dni, imagen, jefe from empleado e join registro_empleados r on e.id_emp = r.empleado_id_emp where r.fecha_baja is null";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['empleado'][]=$registro;
			//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
		}
		mysqli_close($conexion);
		echo json_encode($json);
		return($json);
		
?>