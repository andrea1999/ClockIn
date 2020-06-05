<?PHP
$hostname ="localhost";
$database ="clockin";
$username ="root";
$password ="";

$json=array();
	$conexion = mysqli_connect($hostname,$username,$password,$database);

		$consulta="SELECT rj.empleado_id_emp, rj.fecha, j.id_jornada, j.tipo, j.ubicacion, j.hora, j.nota from registro_jornada rj join jornada j on rj.jornada_id_jornada = j.id_jornada";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['registro'][]=$registro;
			//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
		}

		mysqli_close($conexion);
		echo json_encode($json);
		return($json);	
?>