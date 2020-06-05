<?PHP
$hostname ="localhost";
$database ="clockin";
$username ="root";
$password ="";

$json=array();

	$conexion = mysqli_connect($hostname,$username,$password,$database);

		$consulta="select max(id_jornada) as jornadas from jornada";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['jornada'][]=$registro;
			//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
		}
		mysqli_close($conexion);
		echo json_encode($json);
		return($json);
		
?>