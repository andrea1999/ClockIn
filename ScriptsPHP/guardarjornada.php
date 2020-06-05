<?PHP
$hostname ="localhost";
$database ="clockin";
$username ="root";
$password ="";
	if(isset($_GET["tipo"]) && isset($_GET["ubicacion"]) && isset($_GET["hora"]) && isset($_GET["nota"])){
		$tipo=$_GET['tipo'];
		$ubicacion=$_GET['ubicacion'];
		$hora=$_GET['hora'];
        $nota=$_GET['nota'];
		
		$conexion=mysqli_connect($hostname,$username,$password,$database);
		
		$insert="INSERT INTO jornada(tipo, ubicacion, hora, nota) VALUES ({$tipo},'{$ubicacion}','{$hora}','{$nota}')";

		$resultado_insert=mysqli_query($conexion,$insert);
	} else {
		
	}
?>