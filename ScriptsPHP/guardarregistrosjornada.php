<?PHP
$hostname ="localhost";
$database ="clockin";
$username ="root";
$password ="";
$json=array();
	if(isset($_GET["tipo"]) && isset($_GET["ubicacion"]) && isset($_GET["hora"]) && isset($_GET["nota"])){
		$tipo=$_GET['tipo'];
		$ubicacion=$_GET['ubicacion'];
		$hora=$_GET['hora'];
        $nota=$_GET['nota'];
		
		$conexion=mysqli_connect($hostname,$username,$password,$database);
		
        $consulta1 = "SELECT count(id_jornada) FROM jornada";
        $resultado_consulta = mysqli_query($conexion,$consulta1);

        if($resultado_consulta=0){
            $id=0;
        } else {
            $id=$resultado_consulta;
        }

		$insert="INSERT INTO usuario(id_jornada, tipo, ubicacion, hora, nota) VALUES ('{$id}','{$tipo}','{$ubicacion}','{$hora}','{$nota}')";
		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$consulta="SELECT * FROM jornada WHERE id_jornada = '{$id}'";
			$resultado=mysqli_query($conexion,$consulta);
			
			if($registro=mysqli_fetch_array($resultado)){
				$json['jornada'][]=$registro;
			}
			mysqli_close($conexion);
			echo json_encode($json);
		}
		else{
			$resulta["id"]=0;
			$resulta["tipo"]='No registra';
			$resulta["ubicacion"]='No Registra';
            $resulta["hora"]='No Registra';
            $resulta["nota"]='No Registra';
			$json['jornada'][]=$resulta;
			echo json_encode($json);
		}
		
	}
	else{
			$resulta["id"]=0;
			$resulta["tipo"]=0;
			$resulta["ubicacion"]='WS No retorna';
            $resulta["hora"]='WS No retorna';
            $resulta["nota"]='WS No retorna';
			$json['jornada'][]=$resulta;
			echo json_encode($json);
		}
?>